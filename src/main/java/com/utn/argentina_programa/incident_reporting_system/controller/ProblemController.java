package com.utn.argentina_programa.incident_reporting_system.controller;

import com.utn.argentina_programa.incident_reporting_system.model.Problem;
import com.utn.argentina_programa.incident_reporting_system.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/problems")
@RequiredArgsConstructor
public class ProblemController {
    private final ProblemService problemService;

    @PostMapping
    public ResponseEntity<Long> createProblem(@RequestBody Problem problem) {
        Long savedProblem = problemService.createProblem(problem);
        return new ResponseEntity<>(savedProblem, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Problem>> getAllProblems() {
        List<Problem> problems = problemService.findAllProblems();
        return new ResponseEntity<>(problems, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Problem> getProblemById(@PathVariable("id") Long id) {
        Problem problem = problemService.findProblemById(id);
        return new ResponseEntity<>(problem, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Long> deleteProblemById(@PathVariable("id") Long id) {
        problemService.deleteProblemById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
