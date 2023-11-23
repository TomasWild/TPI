package com.utn.argentina_programa.incident_reporting_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "technician")
public class Technician {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ElementCollection
    private List<String> skills = new ArrayList<>();
    @Enumerated(value = EnumType.STRING)
    @Column(name = "means_of_notification")
    private MeansOfNotification meansOfNotification;
    private boolean isAvailable;

    public Technician(String name,
                      List<String> skills,
                      MeansOfNotification meansOfNotification,
                      boolean isAvailable) {
        this.name = name;
        this.skills = skills;
        this.meansOfNotification = meansOfNotification;
        this.isAvailable = isAvailable;
    }
}
