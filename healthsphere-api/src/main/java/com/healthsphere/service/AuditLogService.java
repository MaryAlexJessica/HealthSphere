package com.healthsphere.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthsphere.model.AuditLog;
import com.healthsphere.model.User;
import com.healthsphere.repository.AuditLogRepository;

@Service
public class AuditLogService {
    private final AuditLogRepository repo;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AuditLogService(AuditLogRepository repo) {
        this.repo = repo;
    }
    /**
     * Get all audit logs
     */
    public List<AuditLog> getAllLogs() {
        return repo.findAll();
    }
    public void log(User user, String action, String targetTable, Long targetId, Map<String, Object> metadata) {
        AuditLog log = new AuditLog();
        log.setUser(user);
        log.setAction(action);
        log.setTargetTable(targetTable);
        log.setTargetId(targetId);
        try {
            log.setMetadata(metadata != null ? objectMapper.writeValueAsString(metadata) : "{}");
        } catch (Exception e) {
            log.setMetadata("{}"); // fallback
        }
        repo.save(log);
    }
    /**
     * Get audit logs filtered by user
     */
    public List<AuditLog> getLogsByUser(Long userId) {
        return repo.findByUserId(userId);
    }
}
