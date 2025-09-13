package com.healthsphere.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.healthsphere.service.DoctorService;
import com.healthsphere.model.Doctor;
import com.healthsphere.model.User;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorsController {

    private final DoctorService service;

    public DoctorsController(DoctorService service) {
        this.service = service;
    }

    // Create a new doctor
    @PostMapping
    public ResponseEntity<Doctor> create(@RequestBody Doctor doctor, @RequestParam(required = false) Long actorId) {
        // You can pass a User object instead of null if you implement authentication
        User actor = null; 
        return ResponseEntity.ok(service.createDoctor(doctor, actor));
    }

    // Get doctor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getDoctor(id));
    }

    // Get all doctors
    @GetMapping
    public ResponseEntity<List<Doctor>> getAll() {
        return ResponseEntity.ok(service.getAllDoctors());
    }
}
