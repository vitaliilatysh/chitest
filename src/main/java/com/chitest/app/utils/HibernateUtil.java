package com.chitest.app.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

@Component
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    private SessionFactory buildSessionFactory() {

        return new Configuration().configure().buildSessionFactory();
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }
}
