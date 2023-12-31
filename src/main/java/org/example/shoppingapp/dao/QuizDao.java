package org.example.shoppingapp.dao;

import org.example.shoppingapp.domain.Quiz;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizDao extends AbstractHibernateDao<Quiz> {
    public QuizDao(){
        this.setClassType(Quiz.class);
    }

    public List<Quiz> getAllQuizzes() {
        return this.getAll();
    }

    public Quiz findQuizById(int id) {
        return this.findById(id);
    }

    public void addQuiz(Quiz item) {
        this.add(item);
    }
}

