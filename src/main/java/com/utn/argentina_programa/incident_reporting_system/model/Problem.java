package com.utn.argentina_programa.incident_reporting_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private List<String> skillsNeeded = new ArrayList<>();
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime maximumResolutionTime;

    public Problem(String typeOfProblem,
                   List<String> skillsNeeded,
                   LocalDateTime maximumResolutionTime) {
        this.typeOfProblem = typeOfProblem;
        this.skillsNeeded = skillsNeeded;
        this.maximumResolutionTime = maximumResolutionTime;
    }
}
