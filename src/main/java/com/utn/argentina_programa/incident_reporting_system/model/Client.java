package com.utn.argentina_programa.incident_reporting_system.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;
    @NotEmpty(message = "Business name cannot be empty.")
    private String businessName;
    @NotEmpty(message = "CUIT cannot be empty.")
    private String cuit;
    @ElementCollection
    @CollectionTable(
        name = "hired_services",
        joinColumns = @JoinColumn(name = "client_id")
    )
    private Set<String> hiredServices = new HashSet<>();

    public Client(String businessName,
                  String cuit,
                  Set<String> hiredServices) {
        this.businessName = businessName;
        this.cuit = cuit;
        this.hiredServices = hiredServices;
    }
}
