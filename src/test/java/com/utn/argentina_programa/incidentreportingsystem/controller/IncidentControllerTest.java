package com.utn.argentina_programa.incidentreportingsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.argentina_programa.incident_reporting_system.IncidentReportingSystemApplication;
import com.utn.argentina_programa.incident_reporting_system.controller.IncidentController;
import com.utn.argentina_programa.incident_reporting_system.model.Client;
import com.utn.argentina_programa.incident_reporting_system.model.Incident;
import com.utn.argentina_programa.incident_reporting_system.model.Problem;
import com.utn.argentina_programa.incident_reporting_system.model.State;
import com.utn.argentina_programa.incident_reporting_system.model.Technician;
import com.utn.argentina_programa.incident_reporting_system.service.IncidentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = IncidentReportingSystemApplication.class)
@WebMvcTest(controllers = IncidentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class IncidentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private IncidentService incidentService;

    @Test
    public void testCreateIncidentApi() throws Exception {
        Incident incident = new Incident(
            new Client(),
            new Technician(),
            new Problem(),
            "Title test",
            "Description test",
            State.IN_PROGRESS,
            LocalDate.now(),
            LocalDate.now().plusDays(1L)
        );
        given(incidentService.createIncident(ArgumentMatchers.any()))
            .willAnswer((invocation -> {
                Incident createdIncident = invocation.getArgument(0);
                return createdIncident.getId();
            }));
        ResultActions response = mockMvc.perform(post("/api/v1/incidents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(incident)));
        response.andExpect(MockMvcResultMatchers.status().isCreated())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetAllIncidentsApi() throws Exception {
        List<Incident> incidents = Arrays.asList(
            new Incident(
                new Client(),
                new Technician(),
                new Problem(),
                "Title test",
                "Description test 1",
                State.IN_PROGRESS,
                LocalDate.now(),
                LocalDate.now().plusDays(1L)),
            new Incident(
                new Client(),
                new Technician(),
                new Problem(),
                "Title test",
                "Description test 2",
                State.IN_PROGRESS,
                LocalDate.now(),
                LocalDate.now().plusDays(3L))
        );
        when(incidentService.findAllIncidents()).thenReturn(incidents);
        ResultActions response = mockMvc.perform(get("/api/v1/incidents")
            .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$", hasSize(incidents.size())))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetIncidentByIdApi() throws Exception {
        Long incidentId = 1L;
        Incident incident = new Incident(
            new Client(),
            new Technician(),
            new Problem(),
            "Title test",
            "Description test",
            State.IN_PROGRESS,
            LocalDate.now(),
            LocalDate.now().plusDays(1L)
        );
        when(incidentService.findIncidentById(incidentId)).thenReturn(incident);
        ResultActions response = mockMvc.perform(get("/api/v1/incidents/{id}", incidentId)
            .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(incident)))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testUpdateIncidentApi() throws Exception {
        Long incidentId = 1L;
        Incident updatedIncident = new Incident(
            new Client(),
            new Technician(),
            new Problem(),
            "Title update test",
            "Description update test",
            State.IN_PROGRESS,
            LocalDate.now(),
            LocalDate.now().plusDays(1L)
        );
        given(incidentService.updateIncident(incidentId, updatedIncident)).willReturn(updatedIncident);
        ResultActions response = mockMvc.perform(put("/api/v1/incidents/{id}", incidentId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedIncident)));
        response.andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(updatedIncident)))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDeleteIncidentByIdApi() throws Exception {
        Long incidentId = 1L;
        doNothing().when(incidentService).deleteIncidentById(incidentId);
        ResultActions response = mockMvc.perform(delete("/api/v1/incidents/{id}", incidentId)
            .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }
}
