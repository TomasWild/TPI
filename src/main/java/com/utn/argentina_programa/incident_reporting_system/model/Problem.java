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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "problem")
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeOfProblem;
    @ElementCollection
    @CollectionTable(
        name = "problem_skills_needed",
        joinColumns = @JoinColumn(name = "problem_id")
    )
    @Column(name = "skills_needed")
    private Set<String> skillsNeeded = new HashSet<>();
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime maximumResolutionTime;

    public Problem(String typeOfProblem,
                   Set<String> skillsNeeded,
                   LocalDateTime maximumResolutionTime) {
        this.typeOfProblem = typeOfProblem;
        this.skillsNeeded = skillsNeeded;
        this.maximumResolutionTime = maximumResolutionTime;
    }
}
