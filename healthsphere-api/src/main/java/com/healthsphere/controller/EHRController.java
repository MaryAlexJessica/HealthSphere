package com.healthsphere.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthsphere.model.EhrRecord;
import com.healthsphere.service.EhrRecordService;

@RestController
@RequestMapping("/api/ehr")
public class EHRController {

    private final EhrRecordService service;

    public EHRController(EhrRecordService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EhrRecord> create(@RequestBody EhrRecord record) {
        return ResponseEntity.ok(service.createRecord(record, null));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<EhrRecord>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(service.getRecordsByPatient(patientId));
    }
}
