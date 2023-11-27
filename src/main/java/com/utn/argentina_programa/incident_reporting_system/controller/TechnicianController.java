package com.utn.argentina_programa.incident_reporting_system.controller;

import com.utn.argentina_programa.incident_reporting_system.model.Technician;
import com.utn.argentina_programa.incident_reporting_system.service.TechnicianService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/technicians")
@RequiredArgsConstructor
public class TechnicianController {
    private final TechnicianService technicianService;

    @PostMapping
    public ResponseEntity<Long> createTechnician(@RequestBody Technician technician) {
        Long savedTechnician = technicianService.createTechnician(technician);
        return new ResponseEntity<>(savedTechnician, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Technician>> getAllTechnicians() {
        List<Technician> technicians = technicianService.findAllTechnicians();
        return new ResponseEntity<>(technicians, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Technician> getTechnicianById(@PathVariable("id") Long id) {
        Technician technician = technicianService.findTechnicianById(id);
        return new ResponseEntity<>(technician, HttpStatus.OK);
    }

    @GetMapping("/availability")
    public ResponseEntity<List<Technician>> getTechniciansByAvailability(@RequestParam("isAvailable") boolean isAvailable) {
        List<Technician> technicians = technicianService.findTechnicianByAvailability(isAvailable);
        return new ResponseEntity<>(technicians, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Technician> updateTechnician(@PathVariable("id") Long id,
                                                       @RequestBody Technician technician) {
        Technician updatedTechnician = technicianService.updateTechnician(id, technician);
        return new ResponseEntity<>(updatedTechnician, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Long> deleteTechnicianById(@PathVariable("id") Long id) {
        technicianService.deleteTechnicianById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
