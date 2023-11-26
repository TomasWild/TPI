package com.utn.argentina_programa.incident_reporting_system.repository;

import com.utn.argentina_programa.incident_reporting_system.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
