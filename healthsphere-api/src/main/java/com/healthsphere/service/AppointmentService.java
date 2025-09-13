package com.healthsphere.service;

import com.healthsphere.model.Appointment;
import com.healthsphere.model.User;
import com.healthsphere.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository repo;
    private final AuditLogService auditLogService;

    public AppointmentService(AppointmentRepository repo, AuditLogService auditLogService) {
        this.repo = repo;
        this.auditLogService = auditLogService;
    }

    // Create a new appointment
    public Appointment createAppointment(Appointment appointment, User actor) {
        Appointment saved = repo.save(appointment);
        if (actor != null) {
            auditLogService.log(actor, "CREATE_APPOINTMENT", "appointments", saved.getId(), null);
        }
        return saved;
    }

    // Get appointment by ID
    public Appointment getAppointment(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID " + id));
    }

    // Get all appointments for a patient
    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return repo.findByPatientId(patientId);
    }

    // Get all appointments for a doctor
    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return repo.findByDoctorId(doctorId);
    }

    // Update appointment status
    public Appointment updateStatus(Long appointmentId, String status, User actor) {
        Appointment appt = repo.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID " + appointmentId));
        appt.setStatus(Enum.valueOf(Appointment.Status.class, status.toUpperCase()));
        repo.save(appt);

        if (actor != null) {
            auditLogService.log(actor, "UPDATE_APPOINTMENT_STATUS", "appointments", appt.getId(), null);
        }

        return appt;
    }
}
