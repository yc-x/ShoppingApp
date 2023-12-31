package org.example.shoppingapp.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="quiz_table")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(name="quiz_name")
    private String quizName;

    @ManyToOne
    @JoinColumn(name="user_id")
    private QuizUser quizUser;
}
