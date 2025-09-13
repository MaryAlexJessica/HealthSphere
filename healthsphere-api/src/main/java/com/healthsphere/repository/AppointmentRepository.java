package com.healthsphere.repository;

import com.healthsphere.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Find all appointments for a given patient
    List<Appointment> findByPatientId(Long patientId);

    // Find all appointments for a given doctor
    List<Appointment> findByDoctorId(Long doctorId);
}
