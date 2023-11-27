package com.utn.argentina_programa.incidentreportingsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.argentina_programa.incident_reporting_system.IncidentReportingSystemApplication;
import com.utn.argentina_programa.incident_reporting_system.controller.ProblemController;
import com.utn.argentina_programa.incident_reporting_system.model.Problem;
import com.utn.argentina_programa.incident_reporting_system.service.ProblemService;
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

import java.time.LocalDateTime;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = IncidentReportingSystemApplication.class)
@WebMvcTest(controllers = ProblemController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProblemControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProblemService problemService;

    @Test
    public void testCreateProblemApi() throws Exception {
        Problem problem = new Problem(
            "Test",
            Set.of("SAP", "Windows"),
            LocalDateTime.now()
        );
        given(problemService.createProblem(ArgumentMatchers.any()))
            .willAnswer((invocation -> {
                Problem createdProblem = invocation.getArgument(0);
                return createdProblem.getId();
            }));
        ResultActions response = mockMvc.perform(post("/api/v1/problems")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(problem)));
        response.andExpect(MockMvcResultMatchers.status().isCreated())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetAllProblemsApi() throws Exception {
        List<Problem> problems = Arrays.asList(
            new Problem("Test", Set.of("SAP", "Windows"), LocalDateTime.now()),
            new Problem("Test 2", Set.of("Tango", "MacOS"), LocalDateTime.now().plusHours(2L))
        );
        when(problemService.findAllProblems()).thenReturn(problems);
        ResultActions response = mockMvc.perform(get("/api/v1/problems")
            .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$", hasSize(problems.size())))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetProblemByIdApi() throws Exception {
        Long problemId = 1L;
        Problem problem = new Problem(
            "Test",
            Set.of("SAP", "Windows"),
            LocalDateTime.now()
        );
        when(problemService.findProblemById(problemId)).thenReturn(problem);
        ResultActions response = mockMvc.perform(get("/api/v1/problems/{id}", problemId)
            .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(problem)))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testUpdateProblemApi() throws Exception {
        Long problemId = 1L;
        Problem updatedProblem = new Problem(
            "Test update",
            Set.of("SAP", "Windows"),
            LocalDateTime.now()
        );
        given(problemService.updateProblem(problemId, updatedProblem)).willReturn(updatedProblem);
        ResultActions response = mockMvc.perform(put("/api/v1/problems/{id}", problemId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedProblem)));
        response.andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(updatedProblem)))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDeleteProblemByIdApi() throws Exception {
        Long problemId = 1L;
        doNothing().when(problemService).deleteProblemById(problemId);
        ResultActions response = mockMvc.perform(delete("/api/v1/problems/{id}", problemId)
            .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }
}
