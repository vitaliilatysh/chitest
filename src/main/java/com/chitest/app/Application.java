package com.chitest.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

//        SessionFactory sessionFactory = new HibernateUtil().getSessionFactory();
//        Session session = sessionFactory.getCurrentSession();
//
//        Transaction tx = session.beginTransaction();
//
//        Contact contact = new Contact();
//        contact.setName("Vasya");
//
//        Email email = new Email();
//        email.setEmail("email@email.com");
//        email.setContact(contact);
//
//        session.save(contact);
//        session.save(email);
//
//        tx.commit();
//
//        Session sessionNew = sessionFactory.openSession();
//        Transaction newOne = sessionNew.beginTransaction();
//        sessionNew.delete(contact);
//        newOne.commit();


    }
}
