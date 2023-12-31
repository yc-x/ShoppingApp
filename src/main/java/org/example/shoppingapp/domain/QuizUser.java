package org.example.shoppingapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="quiz_user_table")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class QuizUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column(nullable = false)
    private String username;
    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column(name="u_password", nullable = false)
    private String password;
    @Column(name="is_active")
    private boolean isActive;

    @JsonIgnore
    @OneToMany(mappedBy = "quizUser", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Quiz> quizList;
}
