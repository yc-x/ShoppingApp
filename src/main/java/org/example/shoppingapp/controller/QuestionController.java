package org.example.shoppingapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingapp.domain.Question;
import org.example.shoppingapp.dto.common.DataResponse;
import org.example.shoppingapp.dto.question.QuestionCreationRequest;
import org.example.shoppingapp.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

//    public QuestionController(QuestionService questionService) {
//        this.questionService = questionService;
//    }

    @GetMapping("/question/all")
    @ResponseBody
    public DataResponse getAllQuestions() {
        return DataResponse.builder()
                .success(true)
                .message("Success")
                .data(questionService.getAllQuestions())
                .build();
    }

    @GetMapping("/question/{id}")
    @ResponseBody
    public DataResponse getQuestionById(@PathVariable int id) {
        return DataResponse.builder()
                .success(true)
                .message("Success")
                .data(questionService.getQuestionById(id))
                .build();
    }

    @PostMapping("/question")
    @ResponseBody
    public DataResponse addQuestion(@Valid @RequestBody QuestionCreationRequest request, BindingResult result) {

        if (result.hasErrors()) return DataResponse.builder()
                .success(false)
                .message("Something went wrong")
                .build();

        Question question = Question.builder()
                .description(request.getDescription())
                .isActive(request.isActive())
                .build();

        questionService.addQuestion(question);

        return DataResponse.builder()
                .success(true)
                .message("Success")
                .build();
    }
}