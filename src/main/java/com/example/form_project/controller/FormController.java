package com.example.form_project.controller;

import com.example.form_project.model.QuestionWrapper;
import com.example.form_project.model.Response;
import com.example.form_project.service.FormService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/form")
public class FormController {

    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createForm(@RequestParam String category, @RequestParam int numQ, @RequestParam String title) {
        return formService.createForm(category, numQ, title);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id) {
        return formService.getFormQuestions(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitForm(@PathVariable Integer id, @RequestBody List<Response> responses) {
        return formService.calculateResult(id, responses);
    }
}