package com.healthsphere.controller;

import com.healthsphere.model.Patient;
import com.healthsphere.model.User;
import com.healthsphere.service.PatientService;
import com.healthsphere.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;
    private final UserService userService; // To get User entity from username

    public PatientController(PatientService patientService, UserService userService) {
        this.patientService = patientService;
        this.userService = userService;
    }

    // ✅ Create a patient profile
    @PostMapping
    public ResponseEntity<Patient> createPatient(
            @RequestBody Patient patient,
            @AuthenticationPrincipal UserDetails userDetails) {

        // Fetch currently authenticated User entity
        User actor = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Patient saved = patientService.save(patient, actor);
        return ResponseEntity.ok(saved);
    }

    // ✅ Get patient profile for logged-in user
    @GetMapping("/me")
    public ResponseEntity<Patient> getMyPatientProfile(
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Patient> patientOpt = patientService.findByUserId(user.getId());
        return patientOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
