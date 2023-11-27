package com.utn.argentina_programa.incident_reporting_system.service;

import com.utn.argentina_programa.incident_reporting_system.exception.IncidentNotFoundException;
import com.utn.argentina_programa.incident_reporting_system.model.Incident;
import com.utn.argentina_programa.incident_reporting_system.repository.IncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidentService {
    private final IncidentRepository incidentRepository;

    public Long createIncident(Incident incident) {
        Incident savedIncident = incidentRepository.save(incident);
        return savedIncident.getId();
    }

    public List<Incident> findAllIncidents() {
        return incidentRepository.findAll();
    }

    public Incident findIncidentById(Long id) {
        return incidentRepository.findById(id)
            .orElseThrow(() -> new IncidentNotFoundException("Incident with ID " + id + " not found."));
    }

    public Incident updateIncident(Long id,
                                  Incident updateIncident) {
        Incident existingIncident = incidentRepository.findById(id)
            .orElseThrow(() -> new IncidentNotFoundException("Incident with ID " + id + " not found."));
        existingIncident.setClient(updateIncident.getClient());
        existingIncident.setTechnician(updateIncident.getTechnician());
        existingIncident.setProblem(updateIncident.getProblem());
        existingIncident.setTitle(updateIncident.getTitle());
        existingIncident.setDescription(updateIncident.getDescription());
        existingIncident.setState(updateIncident.getState());
        existingIncident.setCreationDate(updateIncident.getCreationDate());
        existingIncident.setResolutionDate(updateIncident.getResolutionDate());
        return incidentRepository.save(existingIncident);
    }

    public void deleteIncidentById(Long id) {
        Incident incident = incidentRepository.findById(id)
            .orElseThrow(() -> new IncidentNotFoundException("Incident with ID " + id + " not found."));
        incidentRepository.deleteById(incident.getId());
    }
}
