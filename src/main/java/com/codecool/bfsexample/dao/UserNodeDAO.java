package com.codecool.bfsexample.dao;

import com.codecool.bfsexample.model.UserNode;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserNodeDAO {

    private EntityManager em;

    public UserNodeDAO(EntityManager em) {
        this.em = em;
    }

    public void addAll(List<UserNode> users) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        for (UserNode user : users) {
            em.persist(user);
        }
        transaction.commit();
    }

    public List<UserNode> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserNode> criteriaQuery = cb.createQuery(UserNode.class);
        Root<UserNode> root = criteriaQuery.from(UserNode.class);
        criteriaQuery.select(root);
        TypedQuery<UserNode> query = em.createQuery(criteriaQuery);

        return query.getResultList();
    }
}
