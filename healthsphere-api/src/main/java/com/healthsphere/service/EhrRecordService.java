package com.healthsphere.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.healthsphere.model.EhrRecord;
import com.healthsphere.model.User;
import com.healthsphere.repository.EhrRecordRepository;

@Service
public class EhrRecordService {

    private final EhrRecordRepository repo;
    private final AuditLogService auditLogService;

    public EhrRecordService(EhrRecordRepository repo, AuditLogService auditLogService) {
        this.repo = repo;
        this.auditLogService = auditLogService;
    }

    // Create new EHR record
    public EhrRecord createRecord(EhrRecord record, User actor) {
        EhrRecord saved = repo.save(record);
        auditLogService.log(actor, "CREATE_EHR_RECORD", "ehr_records", saved.getId(), null);
        return saved;
    }

    // Fetch all records for a patient
    public List<EhrRecord> getRecordsByPatient(Long patientId) {
        return repo.findByPatientId(patientId);
    }

    // Optional: fetch a single record by ID
    public EhrRecord getRecord(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("EHR record not found with id " + id));
    }
}
