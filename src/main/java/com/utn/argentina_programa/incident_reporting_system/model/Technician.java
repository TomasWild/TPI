package com.utn.argentina_programa.incident_reporting_system.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "technician")
public class Technician {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "technician_id")
    private Long id;
    @NotEmpty(message = "Name cannot be empty.")
    private String name;
    @ElementCollection(targetClass = String.class)
    @CollectionTable(
        name = "technician_skills",
        joinColumns = @JoinColumn(name = "technician_id")
    )
    private Set<String> skills = new HashSet<>();
    @Enumerated(value = EnumType.STRING)
    private MeansOfNotification meansOfNotification;
    private boolean isAvailable;

    public Technician(String name,
                      Set<String> skills,
                      MeansOfNotification meansOfNotification,
                      boolean isAvailable) {
        this.name = name;
        this.skills = skills;
        this.meansOfNotification = meansOfNotification;
        this.isAvailable = isAvailable;
    }
}
