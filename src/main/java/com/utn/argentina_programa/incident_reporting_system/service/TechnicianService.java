package com.utn.argentina_programa.incident_reporting_system.service;

import com.utn.argentina_programa.incident_reporting_system.exception.TechnicianNotFoundException;
import com.utn.argentina_programa.incident_reporting_system.model.Technician;
import com.utn.argentina_programa.incident_reporting_system.repository.TechnicianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnicianService {
    private final TechnicianRepository technicianRepository;

    public Long createTechnician(Technician technician) {
        Technician savedTechnician = technicianRepository.save(technician);
        return savedTechnician.getId();
    }

    public List<Technician> findAllTechnicians() {
        return technicianRepository.findAll();
    }

    public Technician findTechnicianById(Long id) {
        return technicianRepository.findById(id)
            .orElseThrow(() -> new TechnicianNotFoundException("Technician with ID " + id + " not found."));
    }

    public List<Technician> findTechnicianByAvailability(boolean isAvailable) {
        return technicianRepository.findTechnicianByAvailability(isAvailable)
            .orElse(Collections.emptyList());
    }

    public void deleteTechnicianById(Long id) {
        Technician technician = technicianRepository.findById(id)
            .orElseThrow(() -> new TechnicianNotFoundException("Technician with ID " + id + " not found."));
        technicianRepository.deleteById(technician.getId());
    }
}
