package org.example.shoppingapp.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public abstract class AbstractHibernateDao<T> {
    @Autowired
    protected SessionFactory sessionFactory;

    protected Class<T> classType;

    protected final void setClassType(final Class<T> classTypeToSet) {
        classType = classTypeToSet;
    }

    public List<T> getAll() {
        Session session = getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(classType);
        criteria.from(classType);
        return session.createQuery(criteria).getResultList();
    }

    public T findById(long id) {
        return getCurrentSession().get(classType, id);
    }

    public void add(T item) {
        getCurrentSession().save(item);
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}