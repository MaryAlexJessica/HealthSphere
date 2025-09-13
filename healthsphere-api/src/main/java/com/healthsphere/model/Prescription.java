package com.healthsphere.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "prescriptions")
public class Prescription {

    public enum Status { ISSUED, CANCELLED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "issued_at", nullable = false, updatable = false)
    private Instant issuedAt = Instant.now();

    @Column(columnDefinition = "JSON")
    private String details;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ISSUED;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public Instant getIssuedAt() { return issuedAt; }
    public void setIssuedAt(Instant issuedAt) { this.issuedAt = issuedAt; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
