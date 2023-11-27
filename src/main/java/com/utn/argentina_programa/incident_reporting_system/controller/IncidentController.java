package com.utn.argentina_programa.incident_reporting_system.controller;

import com.utn.argentina_programa.incident_reporting_system.model.Incident;
import com.utn.argentina_programa.incident_reporting_system.service.IncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/incidents")
@RequiredArgsConstructor
public class IncidentController {
    private final IncidentService incidentService;

    @PostMapping
    public ResponseEntity<Long> createIncident(@RequestBody Incident incident) {
        Long savedIncident = incidentService.createIncident(incident);
        return new ResponseEntity<>(savedIncident, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Incident>> getAllIncidents() {
        List<Incident> incidents = incidentService.findAllIncidents();
        return new ResponseEntity<>(incidents, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Incident> getIncidentById(@PathVariable("id") Long id) {
        Incident incident = incidentService.findIncidentById(id);
        return new ResponseEntity<>(incident, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Incident> updateIncident(@PathVariable("id") Long id,
                                                   @RequestBody Incident incident) {
        Incident updatedIncident = incidentService.updateIncident(id, incident);
        return new ResponseEntity<>(updatedIncident, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Incident> deleteIncidentById(@PathVariable("id") Long id) {
        incidentService.deleteIncidentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
