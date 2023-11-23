package com.utn.argentina_programa.incident_reporting_system.repository;

import com.utn.argentina_programa.incident_reporting_system.model.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Long> {
    @Query("SELECT t FROM Technician t WHERE t.isAvailable = :isAvailable")
    Optional<List<Technician>> findTechnicianByAvailability(boolean isAvailable);
}
