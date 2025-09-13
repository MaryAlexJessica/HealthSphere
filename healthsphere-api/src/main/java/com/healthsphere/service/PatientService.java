package com.healthsphere.service;

import com.healthsphere.model.Patient;
import com.healthsphere.model.User;
import com.healthsphere.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {
    private final PatientRepository repo;
    private final AuditLogService auditLogService;

    public PatientService(PatientRepository repo, AuditLogService auditLogService) {
        this.repo = repo;
        this.auditLogService = auditLogService;
    }

    public Patient save(Patient patient, User actor) {
        Patient saved = repo.save(patient);
        auditLogService.log(actor, "CREATE_PATIENT", "patients", saved.getId(), null);
        return saved;
    }

    public Optional<Patient> findByUserId(Long userId) {
        return repo.findByUserId(userId);
    }
}
