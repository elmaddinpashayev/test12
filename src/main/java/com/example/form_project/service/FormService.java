package com.example.form_project.service;

import com.example.form_project.model.Form;
import com.example.form_project.model.Question;
import com.example.form_project.model.QuestionWrapper;
import com.example.form_project.model.Response;
import com.example.form_project.repository.FormRepository;
import com.example.form_project.repository.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FormService {

    private final FormRepository formRepository;
    private final QuestionRepository questionRepository;

    public FormService(FormRepository formRepository, QuestionRepository questionRepository) {
        this.formRepository = formRepository;
        this.questionRepository = questionRepository;
    }

    public ResponseEntity<String> createForm(String category, int numQ, String title) {

        List<Question> questions = questionRepository.findRandomQuestionsByCategory(category, numQ);

        Form form = new Form();
        form.setTitle(title);
        form.setQuestions(questions);
        formRepository.save(form);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getFormQuestions(Integer id) {
        Optional<Form> form = formRepository.findById(id);
        List<Question> questionsFromDB = form.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for (Question q : questionsFromDB) {
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(qw);
        }

        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);

    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Form form = formRepository.findById(id).get();
        List<Question> questions = form.getQuestions();
        int right = 0;
        int i = 0;
        for(Response response : responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer()))
                right++;

            i++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}