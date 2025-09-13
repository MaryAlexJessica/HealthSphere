package com.healthsphere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthsphere.model.Doctor;
import com.healthsphere.model.EhrRecord;

public interface EhrRecordRepository extends JpaRepository<EhrRecord, Long> {
    List<EhrRecord> findByPatientId(Long patientId);
    List<EhrRecord> findByDoctor(Doctor doctor);
}
