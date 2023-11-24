package com.utn.argentina_programa.incidentreportingsystem.service;

import com.utn.argentina_programa.incident_reporting_system.exception.TechnicianNotFoundException;
import com.utn.argentina_programa.incident_reporting_system.model.MeansOfNotification;
import com.utn.argentina_programa.incident_reporting_system.model.Technician;
import com.utn.argentina_programa.incident_reporting_system.repository.TechnicianRepository;
import com.utn.argentina_programa.incident_reporting_system.service.TechnicianService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TechnicianServiceTest {
    @Mock
    private TechnicianRepository technicianRepository;
    @InjectMocks
    private TechnicianService technicianService;

    @Test
    public void createTechnicianTest() {
        Technician technician = new Technician(
            1L,
            "Technician Test",
            Set.of(),
            MeansOfNotification.EMAIL,
            true
        );
        when(technicianRepository.save(technician)).thenReturn(technician);
        Long technicianId = technicianService.createTechnician(technician);
        assertNotNull(technicianId);
    }

    @Test
    public void testFindAllTechnicians() {
        List<Technician> expectedTechnicians = Arrays.asList(
            new Technician(1L, "Technician Test 1", Set.of(), MeansOfNotification.EMAIL, true),
            new Technician(2L, "Technician Test 2", Set.of(), MeansOfNotification.WHATSAPP, false)
        );
        when(technicianRepository.findAll()).thenReturn(expectedTechnicians);
        List<Technician> actualTechnicians = technicianService.findAllTechnicians();
        assertEquals(expectedTechnicians.size(), actualTechnicians.size());
    }

    @Test
    public void testFindTechnicianById() {
        Long technicianId = 1L;
        Technician expectedTechnician = new Technician(
            "Technician Test",
            Set.of(),
            MeansOfNotification.EMAIL,
            true
        );
        when(technicianRepository.findById(technicianId)).thenReturn(java.util.Optional.of(expectedTechnician));
        Technician actualTechnician = technicianService.findTechnicianById(technicianId);
        assertEquals(expectedTechnician, actualTechnician);
    }

    @Test
    public void testFindTechnicianByIdNotFound() {
        Long technicianId = 1L;
        when(technicianRepository.findById(technicianId)).thenReturn(Optional.empty());
        assertThrows(TechnicianNotFoundException.class, () -> technicianService.findTechnicianById(technicianId));
    }

    @Test
    public void testDeleteTechnicianById() {
        Long technicianId = 1L;
        Technician technician = new Technician(
            "Technician Test",
            Set.of(),
            MeansOfNotification.EMAIL,
            true
        );
        when(technicianRepository.findById(technicianId)).thenReturn(Optional.of(technician));
        technicianService.deleteTechnicianById(technicianId);
        verify(technicianRepository, times(1)).deleteById(technician.getId());
    }
}
