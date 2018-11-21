package com.chitest.app.repositories;

import com.chitest.app.entities.Contact;
import com.chitest.app.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Integer> {

    Contact findByName(String name);

    List<Contact> findAllByUser(User user);

}
