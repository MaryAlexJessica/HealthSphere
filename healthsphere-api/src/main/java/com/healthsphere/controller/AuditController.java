package com.healthsphere.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.healthsphere.service.AuditLogService;
import com.healthsphere.model.AuditLog;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    private final AuditLogService service;

    public AuditController(AuditLogService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AuditLog>> getAll() {
        return ResponseEntity.ok(service.getAllLogs());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditLog>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getLogsByUser(userId));
    }
}
