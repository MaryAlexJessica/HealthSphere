package com.healthsphere.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthsphere.dto.UserResponse;
import com.healthsphere.model.User;
import com.healthsphere.repository.UserRepository;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository repo;

    public AdminController(UserRepository repo) {
        this.repo = repo;
    }
// Strip ROLE_ prefix if present
private String normalizeRole(String role) {
    if (role == null) return null;
    return role.startsWith("ROLE_") ? role.substring(5) : role;
}
    // ✅ GET all pending doctors
    @GetMapping("/doctors/pending")
    public List<UserResponse> getPendingDoctors() {
         return repo.findByRoleAndStatus(User.Role.DOCTOR, User.Status.PENDING)
               .stream()
               .map(u -> new UserResponse(
                       u.getId(),
                       u.getUsername(),
                       u.getRole().name(),
                       u.getStatus().name()
               ))
               .collect(Collectors.toList());
    }

        @PostMapping("/approve/{id}")
    public ResponseEntity<UserResponse> approveDoctor(@PathVariable Long id) {
        User doctor = repo.findById(id).orElseThrow(() -> new RuntimeException("Doctor not found with id " + id));
        doctor.setStatus(User.Status.ACTIVE);
        repo.save(doctor);

        return ResponseEntity.ok(
            new UserResponse(
                doctor.getId(),
                doctor.getUsername(),
                doctor.getRole().name(),
                doctor.getStatus().name()
            )
        );
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<UserResponse> rejectDoctor(@PathVariable Long id) {
        User doctor = repo.findById(id).orElseThrow(() -> new RuntimeException("Doctor not found with id " + id));
        doctor.setStatus(User.Status.REJECTED);
        repo.save(doctor);
        return ResponseEntity.ok(
            new UserResponse(
                doctor.getId(),
                doctor.getUsername(),
                doctor.getRole().name(),
                doctor.getStatus().name()
            )
        );

    }
}
