package com.utn.argentina_programa.incidentreportingsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.argentina_programa.incident_reporting_system.IncidentReportingSystemApplication;
import com.utn.argentina_programa.incident_reporting_system.controller.TechnicianController;
import com.utn.argentina_programa.incident_reporting_system.model.MeansOfNotification;
import com.utn.argentina_programa.incident_reporting_system.model.Technician;
import com.utn.argentina_programa.incident_reporting_system.service.TechnicianService;
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

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ContextConfiguration(classes = IncidentReportingSystemApplication.class)
@WebMvcTest(controllers = TechnicianController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class TechnicianControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TechnicianService technicianService;

    @Test
    public void testCreateTechnicianApi() throws Exception {
        Technician technician = new Technician(
            "Technician Test",
            Set.of(),
            MeansOfNotification.EMAIL,
            true
        );
        given(technicianService.createTechnician(ArgumentMatchers.any()))
            .willAnswer((invocation -> {
                Technician createdTechnician = invocation.getArgument(0);
                return createdTechnician.getId();
            }));
        ResultActions response = mockMvc.perform(post("/api/v1/technicians")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(technician)));
        response.andExpect(MockMvcResultMatchers.status().isCreated())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetAllTechniciansApi() throws Exception {
        List<Technician> technicians = Arrays.asList(
            new Technician("Technician Test 1", Set.of(), MeansOfNotification.EMAIL, true),
            new Technician("Technician Test 2", Set.of(), MeansOfNotification.WHATSAPP, true)
        );
        when(technicianService.findAllTechnicians()).thenReturn(technicians);
        ResultActions response = mockMvc.perform(get("/api/v1/technicians")
            .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$", hasSize(technicians.size())))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetTechnicianByIdApi() throws Exception {
        Long technicianId = 1L;
        Technician technician = new Technician(
            "Technician Test",
            Set.of(),
            MeansOfNotification.EMAIL,
            true
        );
        when(technicianService.findTechnicianById(technicianId)).thenReturn(technician);
        ResultActions response = mockMvc.perform(get("/api/v1/technicians/{id}", technicianId)
            .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(technician)))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDeleteTechnicianByIdApi() throws Exception {
        Long technicianId = 1L;
        doNothing().when(technicianService).deleteTechnicianById(technicianId);
        ResultActions response = mockMvc.perform(delete("/api/v1/technicians/{id}", technicianId)
            .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }
}
