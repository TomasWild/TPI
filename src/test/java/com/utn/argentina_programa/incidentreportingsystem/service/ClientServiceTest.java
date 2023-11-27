package com.utn.argentina_programa.incidentreportingsystem.service;

import com.utn.argentina_programa.incident_reporting_system.exception.ClientNotFoundException;
import com.utn.argentina_programa.incident_reporting_system.model.Client;
import com.utn.argentina_programa.incident_reporting_system.repository.ClientRepository;
import com.utn.argentina_programa.incident_reporting_system.service.ClientService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;
    @InjectMocks
    private ClientService clientService;

    @Test
    public void testCreateClient() {
        Client client = new Client(
            1L,
            "Client Test",
            "123456789",
            Set.of("SAP", "Windows")
        );
        when(clientRepository.save(client)).thenReturn(client);
        Long clientId = clientService.createClient(client);
        assertNotNull(clientId);
    }

    @Test
    public void testFindAllClients() {
        List<Client> expectedClients = Arrays.asList(
            new Client(1L, "Client Test 1", "123456789", Set.of("SAP", "Windows")),
            new Client(2L, "Client Test 2", "987654321", Set.of("Tango", "MacOS"))
        );
        when(clientRepository.findAll()).thenReturn(expectedClients);
        List<Client> actualClients = clientService.findAllClients();
        assertEquals(expectedClients.size(), actualClients.size());
    }

    @Test
    public void testFindClientById() {
        Long clientId = 1L;
        Client expectedClient = new Client(
            "Client Test",
            "123456789",
            Set.of("SAP", "Windows")
        );
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(expectedClient));
        Client actualClient = clientService.findClientById(clientId);
        assertEquals(expectedClient, actualClient);
    }

    @Test
    public void testFindClientByIdNotFound() {
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundException.class, () -> clientService.findClientById(clientId));
    }

    @Test
    public void testUpdateClientSuccess() {
        Long clientId = 1L;
        Client existingClient = new Client(
            1L,
            "Client Test 1",
            "123456789",
            Set.of("SAP", "Windows")
        );
        Client updateClient = new Client(
            1L,
            "Client Update Test",
            "123456789",
            Set.of("SAP", "Windows")
        );
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(existingClient));
        when(clientRepository.save(any())).thenReturn(existingClient);
        Client result = clientService.updateClient(clientId, updateClient);
        assertEquals(updateClient.getBusinessName(), existingClient.getBusinessName());
        assertEquals(updateClient.getCuit(), existingClient.getCuit());
        assertEquals(updateClient.getHiredServices(), existingClient.getHiredServices());
        verify(clientRepository, times(1)).findById(clientId);
        verify(clientRepository, times(1)).save(existingClient);
    }

    @Test
    public void testUpdateClientNotFound() {
        Long clientId = 1L;
        Client updateClient = new Client(
            "Client Update Test",
            "123456789",
            Set.of("SAP", "Windows")
        );
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundException.class, () -> clientService.updateClient(clientId, updateClient));
        verify(clientRepository, times(1)).findById(clientId);
        verify(clientRepository, never()).save(any());
    }

    @Test
    public void testDeleteClientById() {
        Long clientId = 1L;
        Client client = new Client(
            "Client Test",
            "123456789",
            Set.of("SAP", "Windows")
        );
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        clientService.deleteClientById(clientId);
        verify(clientRepository, times(1)).deleteById(client.getId());
    }
}
