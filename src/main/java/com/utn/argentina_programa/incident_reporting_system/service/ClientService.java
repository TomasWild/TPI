package com.utn.argentina_programa.incident_reporting_system.service;

import com.utn.argentina_programa.incident_reporting_system.exception.ClientNotFoundException;
import com.utn.argentina_programa.incident_reporting_system.model.Client;
import com.utn.argentina_programa.incident_reporting_system.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public Long createClient(Client client) {
        Client savedClient = clientRepository.save(client);
        return savedClient.getId();
    }

    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    public Client findClientById(Long id) {
        return clientRepository.findById(id)
            .orElseThrow(() -> new ClientNotFoundException("Client with ID " + id + " not found."));
    }

    public void deleteClientById(Long id) {
        Client client = clientRepository.findById(id)
            .orElseThrow(() -> new ClientNotFoundException("Client with ID " + id + " not found."));
        clientRepository.deleteById(client.getId());
    }
}
