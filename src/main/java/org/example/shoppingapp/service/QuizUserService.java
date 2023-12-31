package org.example.shoppingapp.service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingapp.dao.QuizDao;
import org.example.shoppingapp.dao.QuizUserDao;
import org.example.shoppingapp.domain.QuizUser;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizUserService {
    private final QuizUserDao quizUserDao;
    private final QuizDao quizDao;

    @Transactional
    public List<QuizUser> getAllQuizUsers(){
        return quizUserDao.getAllQuizUsers();
    }

    @Transactional
    public QuizUser findQuizUserById(int userId){
        return quizUserDao.findQuizUserById(userId);
    }

    @Transactional
    public void addQuizUser(QuizUser quizUser){
        quizUserDao.addQuizUser(quizUser);
    }

    @Transactional
    public void deleteQuizUserById(int quizUserId){
        quizUserDao.deleteQuizUserById(quizUserId);
    }

    @Transactional
    public void updateQuizUserActivenessById(int quizUserId, boolean isActive){
        quizUserDao.updateQuizUserActivenessById(quizUserId, isActive);
    }

}
