package com.utn.argentina_programa.incident_reporting_system.service;

import com.utn.argentina_programa.incident_reporting_system.exception.ProblemNotFoundException;
import com.utn.argentina_programa.incident_reporting_system.model.Problem;
import com.utn.argentina_programa.incident_reporting_system.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;

    public Long createProblem(Problem problem) {
        Problem savedProblem = problemRepository.save(problem);
        return savedProblem.getId();
    }

    public List<Problem> findAllProblems() {
        return problemRepository.findAll();
    }

    public Problem findProblemById(Long id) {
        return problemRepository.findById(id)
            .orElseThrow(() -> new ProblemNotFoundException("Problem with ID " + id + " not found."));
    }

    public Problem updateProblem(Long id,
                                 Problem updateProblem) {
        Problem existingProblem = problemRepository.findById(id)
            .orElseThrow(() -> new ProblemNotFoundException("Problem with ID " + id + " not found."));
        existingProblem.setTypeOfProblem(updateProblem.getTypeOfProblem());
        existingProblem.setSkillsNeeded(updateProblem.getSkillsNeeded());
        existingProblem.setMaximumResolutionTime(updateProblem.getMaximumResolutionTime());
        return problemRepository.save(existingProblem);
    }

    public void deleteProblemById(Long id) {
        Problem problem = problemRepository.findById(id)
            .orElseThrow(() -> new ProblemNotFoundException("Problem with ID " + id + " not found."));
        problemRepository.deleteById(problem.getId());
    }
}
