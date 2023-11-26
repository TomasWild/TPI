package com.utn.argentina_programa.incidentreportingsystem.service;

import com.utn.argentina_programa.incident_reporting_system.exception.ProblemNotFoundException;
import com.utn.argentina_programa.incident_reporting_system.model.Problem;
import com.utn.argentina_programa.incident_reporting_system.repository.ProblemRepository;
import com.utn.argentina_programa.incident_reporting_system.service.ProblemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProblemServiceTest {
    @Mock
    private ProblemRepository problemRepository;
    @InjectMocks
    private ProblemService problemService;

    @Test
    public void testCreateProblem() {
        Problem problem = new Problem(
            1L,
            "Test",
            Set.of("SAP", "Windows"),
            LocalDateTime.now()
        );
        when(problemRepository.save(problem)).thenReturn(problem);
        Long problemId = problemService.createProblem(problem);
        assertNotNull(problemId);
    }

    @Test
    public void testFindAllProblems() {
        List<Problem> expectedProblems = Arrays.asList(
            new Problem(1L, "Test 1", Set.of("SAP", "Windows"), LocalDateTime.now()),
            new Problem(2L, "Test 2", Set.of("Tango", "MacOS"), LocalDateTime.now().plusHours(2L))
        );
        when(problemRepository.findAll()).thenReturn(expectedProblems);
        List<Problem> actualProblems = problemService.findAllProblems();
        assertEquals(expectedProblems.size(), actualProblems.size());
    }

    @Test
    public void testFindProblemById() {
        Long problemId = 1L;
        Problem expectedProblem = new Problem(
            "Test",
            Set.of("SAP", "Windows"),
            LocalDateTime.now()
        );
        when(problemRepository.findById(problemId)).thenReturn(Optional.of(expectedProblem));
        Problem actualProblem = problemService.findProblemById(problemId);
        assertEquals(expectedProblem, actualProblem);
    }

    @Test
    public void testFindProblemByIdNotFound() {
        Long problemId = 1L;
        when(problemRepository.findById(problemId)).thenReturn(Optional.empty());
        assertThrows(ProblemNotFoundException.class, () -> problemService.findProblemById(problemId));
    }

    @Test
    public void testDeleteProblemById() {
        Long problemId = 1L;
        Problem problem = new Problem(
            "Test",
            Set.of("SAP", "Windows"),
            LocalDateTime.now()
        );
        when(problemRepository.findById(problemId)).thenReturn(Optional.of(problem));
        problemService.deleteProblemById(problemId);
        verify(problemRepository, times(1)).deleteById(problem.getId());
    }
}
