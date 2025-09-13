package com.healthsphere.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.healthsphere.model.Doctor;
import com.healthsphere.model.User;
import com.healthsphere.repository.DoctorRepository;
import com.healthsphere.repository.UserRepository;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepo;
    private final UserRepository userRepo;
    private final AuditLogService auditLogService;

    public DoctorService(DoctorRepository doctorRepo, UserRepository userRepo, AuditLogService auditLogService) {
        this.doctorRepo = doctorRepo;
        this.userRepo = userRepo;
        this.auditLogService = auditLogService;
    }

    // Create doctor profile
    public Doctor createDoctor(Doctor doctor, User actor) {
        Doctor saved = doctorRepo.save(doctor);
        if (actor != null) {
            auditLogService.log(actor, "CREATE_DOCTOR", "doctors", saved.getId(), null);
        }
        return saved;
    }

    // Get doctor by ID
    public Doctor getDoctor(Long id) {
        return doctorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID " + id));
    }

    // Get all doctors
    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    // Get all pending doctors (status in User entity)
    public List<User> getPendingDoctors() {
        return userRepo.findByRoleAndStatus(User.Role.DOCTOR, User.Status.PENDING);
    }

    // Approve doctor (updates User status)
    public User approveDoctor(Long userId, User actor) {
        User doctorUser = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Doctor user not found with ID " + userId));
        doctorUser.setStatus(User.Status.ACTIVE);
        userRepo.save(doctorUser);

        if (actor != null) {
            auditLogService.log(actor, "APPROVE_DOCTOR", "users", doctorUser.getId(), null);
        }

        return doctorUser;
    }

    // Reject doctor (updates User status)
    public User rejectDoctor(Long userId, User actor) {
        User doctorUser = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Doctor user not found with ID " + userId));
        doctorUser.setStatus(User.Status.REJECTED);
        userRepo.save(doctorUser);

        if (actor != null) {
            auditLogService.log(actor, "REJECT_DOCTOR", "users", doctorUser.getId(), null);
        }

        return doctorUser;
    }
}
