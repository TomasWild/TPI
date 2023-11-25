package com.utn.argentina_programa.incidentreportingsystem.service;

import com.utn.argentina_programa.incident_reporting_system.exception.IncidentNotFoundException;
import com.utn.argentina_programa.incident_reporting_system.model.Client;
import com.utn.argentina_programa.incident_reporting_system.model.Incident;
import com.utn.argentina_programa.incident_reporting_system.model.MeansOfNotification;
import com.utn.argentina_programa.incident_reporting_system.model.Problem;
import com.utn.argentina_programa.incident_reporting_system.model.State;
import com.utn.argentina_programa.incident_reporting_system.model.Technician;
import com.utn.argentina_programa.incident_reporting_system.repository.IncidentRepository;
import com.utn.argentina_programa.incident_reporting_system.service.IncidentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class IncidentServiceTest {
    @Mock
    private IncidentRepository incidentRepository;
    @InjectMocks
    private IncidentService incidentService;

    @Test
    public void testCreateIncident() {
        Incident incident = new Incident(
            1L,
            new Client(),
            new Technician(),
            new Problem(),
            "Title test",
            "Description test",
            State.IN_PROGRESS,
            LocalDate.now(),
            LocalDate.now().plusDays(7L)
        );
        when(incidentRepository.save(incident)).thenReturn(incident);
        Long incidentId = incidentService.createIncident(incident);
        assertNotNull(incidentId);
    }

    @Test
    public void testFindAllIncidents() {
        List<Incident> expectedIncidents = Arrays.asList(
            new Incident(
                new Client("Client Test 1", "123456789", Set.of("SAP", "Windows")),
                new Technician("Technician Test 1", Set.of(), MeansOfNotification.EMAIL, true),
                new Problem("Problem test 1", Set.of(), LocalDateTime.now().plusMonths(1L)),
                "Title test",
                "Description test 1",
                State.IN_PROGRESS,
                LocalDate.now(),
                LocalDate.now().plusDays(8L)
            ),
            new Incident(
                new Client("Client Test 2", "987654321", Set.of("Tango", "MacOS")),
                new Technician("Technician Test 2", Set.of(), MeansOfNotification.WHATSAPP, false),
                new Problem("Problem test 2", Set.of(), LocalDateTime.now().plusHours(2L)),
                "Title test",
                "Description test 2",
                State.IN_PROGRESS,
                LocalDate.now(),
                LocalDate.now().plusDays(3L)
            )
        );
        when(incidentRepository.findAll()).thenReturn(expectedIncidents);
        List<Incident> actualIncidents = incidentService.findAllIncidents();
        assertEquals(expectedIncidents.size(), actualIncidents.size());
    }

    @Test
    public void testFindIncidentById() {
        Long incidentId = 1L;
        Incident expectedIncident = new Incident();
        when(incidentRepository.findById(incidentId)).thenReturn(Optional.of(expectedIncident));
        Incident actualIncident = incidentService.findIncidentById(incidentId);
        assertEquals(expectedIncident, actualIncident);
    }

    @Test
    public void testFindIncidentByIdNotFound() {
        Long incidentId = 1L;
        when(incidentRepository.findById(incidentId)).thenReturn(Optional.empty());
        assertThrows(IncidentNotFoundException.class, () -> incidentService.findIncidentById(incidentId));
    }

    @Test
    public void testDeleteIncidentById() {
        Long incidentId = 1L;
        Incident incident = new Incident();
        when(incidentRepository.findById(incidentId)).thenReturn(Optional.of(incident));
        incidentService.deleteIncidentById(incidentId);
        verify(incidentRepository, times(1)).deleteById(incident.getId());
    }
}
