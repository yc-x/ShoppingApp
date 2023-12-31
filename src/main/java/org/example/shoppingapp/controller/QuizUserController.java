package org.example.shoppingapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.example.shoppingapp.domain.Question;
import org.example.shoppingapp.domain.QuizUser;
import org.example.shoppingapp.dto.common.DataResponse;
import org.example.shoppingapp.dto.question.QuestionCreationRequest;
import org.example.shoppingapp.dto.quizuser.QuizUserCreationRequest;
import org.example.shoppingapp.service.QuizUserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class QuizUserController {
    private final QuizUserService quizUserService;

    @PostMapping("/user")
    public DataResponse createUser(@Valid @RequestBody QuizUserCreationRequest request, BindingResult result){
        if (result.hasErrors()) {
            return DataResponse.builder()
                    .success(false)
                    .message("Something went wrong, check your format")
                    .build();
        }
        QuizUser quizUser = QuizUser.builder()
                .username(request.getUsername())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(request.getPassword())
                .isActive(true)
                .build();

        quizUserService.addQuizUser(quizUser);

        return DataResponse.builder()
                .success(true)
                .message("Success")
                .build();
    }

    @GetMapping("/user")
    @ResponseBody
    public DataResponse getUserById(@RequestParam(required = false) Integer userId){
        if(userId == null){
            return DataResponse.builder()
                    .success(true)
                    .message("Successfully Get All Users")
                    .data(quizUserService.getAllQuizUsers())
                    .build();
        }
        return DataResponse.builder()
                .success(true)
                .message("Successfully get user by id")
                .data(quizUserService.findQuizUserById(userId))
                .build();
    }

    @DeleteMapping("/user")
    public DataResponse deleteUserById(@RequestParam Integer userId){
        if(userId == null){
            return DataResponse.builder()
                    .success(false)
                    .message("Something went wrong, check your format")
                    .build();
        }
        quizUserService.deleteQuizUserById(userId);
        return DataResponse.builder()
                .success(true)
                .message("Successfully delete user!")
                .build();
    }

    @PatchMapping("/user/{userId}/status")
    public DataResponse updateUserActiveness(@PathVariable("userId") int userId, @RequestParam(name="activate") boolean isActive){
        quizUserService.updateQuizUserActivenessById(userId, isActive);
        return DataResponse.builder()
                .success(true)
                .message("Successfully update user active status to: " + isActive)
                .build();
    }
}
