package com.chitest.app.repositories;

import com.chitest.app.entities.Contact;
import com.chitest.app.entities.Email;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends CrudRepository<Email, Integer> {

    Email findByEmail(String email);

}
