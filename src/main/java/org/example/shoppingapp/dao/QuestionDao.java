package org.example.shoppingapp.dao;

import org.example.shoppingapp.domain.Question;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionDao extends AbstractHibernateDao<Question> {

    public QuestionDao() {
        setClassType(Question.class);
    }

    public Question getQuestionById(int id) {
        return this.findById(id);
    }

    public List<Question> getAllQuestions() {
        return this.getAll();
    }

    public void addQuestion(Question question) {
        this.add(question);
    }

}

