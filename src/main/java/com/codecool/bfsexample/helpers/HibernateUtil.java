package com.codecool.bfsexample.helpers;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bfsExampleUnit");

    public static EntityManagerFactory getEnityManagerFactory() {
        return emf;
    }
}
