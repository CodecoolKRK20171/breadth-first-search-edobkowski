package com.codecool.bfsexample;

import com.codecool.bfsexample.dao.UserNodeDAO;
import com.codecool.bfsexample.helpers.GraphPlotter;
import com.codecool.bfsexample.helpers.HibernateUtil;
import com.codecool.bfsexample.helpers.RandomDataGenerator;
import com.codecool.bfsexample.model.UserNode;

import javax.persistence.EntityManager;
import java.util.List;

public class BFSExample {

    public static void populateDB(UserNodeDAO userNodeDAO) {
        RandomDataGenerator generator = new RandomDataGenerator();
        List<UserNode> users = generator.generate();
        userNodeDAO.addAll(users);
    }

    public static void main(String[] args) {
        EntityManager em = HibernateUtil.getEnityManagerFactory().createEntityManager();
        UserNodeDAO userNodeDAO = new UserNodeDAO(em);

        em.clear();
        populateDB(userNodeDAO);
        List<UserNode> users = userNodeDAO.getAll();
        GraphPlotter.plot(users);

        System.out.println("Done!");
    }

    public static int findDistance(UserNode start, UserNode end) {

        return -1;
    }
}
