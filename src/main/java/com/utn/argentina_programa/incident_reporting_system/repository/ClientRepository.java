package com.utn.argentina_programa.incident_reporting_system.repository;

import com.utn.argentina_programa.incident_reporting_system.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
