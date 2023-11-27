package com.utn.argentina_programa.incidentreportingsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.argentina_programa.incident_reporting_system.IncidentReportingSystemApplication;
import com.utn.argentina_programa.incident_reporting_system.controller.ClientController;
import com.utn.argentina_programa.incident_reporting_system.model.Client;
import com.utn.argentina_programa.incident_reporting_system.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = IncidentReportingSystemApplication.class)
@WebMvcTest(controllers = ClientController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ClientService clientService;

    @Test
    public void testCreateClientApi() throws Exception {
        Client client = new Client(
            "Client Test",
            "123456789",
            Set.of("SAP", "Windows")
        );
        given(clientService.createClient(any()))
            .willAnswer((invocation -> {
                Client createdClient = invocation.getArgument(0);
                return createdClient.getId();
            }));
        ResultActions response = mockMvc.perform(post("/api/v1/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(client)));
        response.andExpect(status().isCreated())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetAllClientsApi() throws Exception {
        List<Client> clients = Arrays.asList(
            new Client("Client Test 1", "123456789", Set.of("SAP", "Windows")),
            new Client("Client Test 2", "987654321", Set.of("Tango", "MacOS"))
        );
        when(clientService.findAllClients()).thenReturn(clients);
        ResultActions response = mockMvc.perform(get("/api/v1/clients")
            .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(clients.size())))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetClientByIdApi() throws Exception {
        Long clientId = 1L;
        Client client = new Client(
            "Client Test",
            "123456789",
            Set.of("SAP", "Windows")
        );
        when(clientService.findClientById(clientId)).thenReturn(client);
        ResultActions response = mockMvc.perform(get("/api/v1/clients/{id}", clientId)
            .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(client)))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testUpdateClientApi() throws Exception {
        Long clientId = 1L;
        Client updatedClient = new Client(
            "Updated Client Test",
            "123456789",
            Set.of("SAP", "Windows")
        );
        given(clientService.updateClient(clientId, updatedClient)).willReturn(updatedClient);
        ResultActions response = mockMvc.perform(put("/api/v1/clients/{id}", clientId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedClient)));
        response.andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(updatedClient)))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDeleteClientByIdApi() throws Exception {
        Long clientId = 1L;
        doNothing().when(clientService).deleteClientById(clientId);
        ResultActions response = mockMvc.perform(delete("/api/v1/clients/{id}", clientId)
            .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }
}
