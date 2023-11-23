package com.utn.argentina_programa.incident_reporting_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "incident")
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Client client;
    private Technician technician;
    private Problem problem;
    private String description;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "state")
    private State state;
    @Column(columnDefinition = "DATE")
    private LocalDate creationDate;
    @Column(columnDefinition = "DATE")
    private LocalDate resolutionDate;

    public Incident(Client client,
                    Technician technician,
                    Problem problem,
                    String description,
                    State state,
                    LocalDate creationDate,
                    LocalDate resolutionDate) {
        this.client = client;
        this.technician = technician;
        this.problem = problem;
        this.description = description;
        this.state = state;
        this.creationDate = creationDate;
        this.resolutionDate = resolutionDate;
    }
}
