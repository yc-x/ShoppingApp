package org.example.shoppingapp.dto.quizuser;

import lombok.*;

@Data
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponse {
    private int id;
    private int userId;
    private String quizName;
    private String userName;
}
