package com.healthsphere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthsphere.model.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByUserId(Long userId);
    List<AuditLog> findByTargetTableAndTargetId(String targetTable, Long targetId);
}
