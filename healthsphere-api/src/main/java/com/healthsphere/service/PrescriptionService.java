package com.healthsphere.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.healthsphere.model.Prescription;
import com.healthsphere.model.User;
import com.healthsphere.repository.PrescriptionRepository;

@Service
public class PrescriptionService {

    private final PrescriptionRepository repo;
    private final AuditLogService auditLogService;

    public PrescriptionService(PrescriptionRepository repo, AuditLogService auditLogService) {
        this.repo = repo;
        this.auditLogService = auditLogService;
    }

    // Create a new prescription
    public Prescription createPrescription(Prescription prescription, User actor) {
        Prescription saved = repo.save(prescription);
        auditLogService.log(actor, "CREATE_PRESCRIPTION", "prescriptions", saved.getId(), null);
        return saved;
    }

    // Get all prescriptions for a given patient
    public List<Prescription> getPrescriptionsByPatient(Long patientId) {
        return repo.findByPatientId(patientId);
    }

    // Optional: get prescriptions by doctor
    public List<Prescription> getPrescriptionsByDoctor(Long doctorId) {
        return repo.findByDoctorId(doctorId);
    }

    // Optional: fetch a single prescription
    public Prescription getPrescription(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with id " + id));
    }
}
