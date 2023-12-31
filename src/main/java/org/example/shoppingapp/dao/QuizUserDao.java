package org.example.shoppingapp.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.example.shoppingapp.domain.Quiz;
import org.example.shoppingapp.domain.QuizUser;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizUserDao extends AbstractHibernateDao<QuizUser>{
    public QuizUserDao() {
        this.setClassType(QuizUser.class);
    }

    public List<QuizUser> getAllQuizUsers() {
        return this.getAll();
    }

    public QuizUser findQuizUserById(int id) {
        return this.findById(id);
    }

    public void addQuizUser(QuizUser item) {
        this.add(item);
    }

    public void deleteQuizUserById(int quizUserId){
        Session currentSession = this.sessionFactory.getCurrentSession();
        QuizUser quizUser = findQuizUserById(quizUserId);
        currentSession.delete(quizUser);
    }

    public void updateQuizUserActivenessById(int quizUserId, boolean isActive){
        Session currentSession = this.sessionFactory.getCurrentSession();
        QuizUser quizUser = findQuizUserById(quizUserId);
        quizUser.setActive(isActive);
        //currentSession.update(quizUser);
        //currentSession.evict(quizUser);
        //currentSession.flush();
    }


    public List<Quiz> getUserQuizzesById(int quizUserId){
        // Session currentSession = this.sessionFactory.getCurrentSession();
        QuizUser quizUser = findQuizUserById(quizUserId);
        return quizUser.getQuizList();
    }
}
