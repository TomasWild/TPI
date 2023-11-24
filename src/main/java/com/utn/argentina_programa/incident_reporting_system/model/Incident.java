package com.utn.argentina_programa.incident_reporting_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

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
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "technician_id")
    private Technician technician;
    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;
    @NotEmpty(message = "You must provided a description of the problem.")
    @Length(
        max = 1000,
        message = "Description must be less than 1000 characters."
    )
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private State state;
    @Temporal(TemporalType.DATE)
    private LocalDate creationDate;
    @Temporal(TemporalType.DATE)
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
