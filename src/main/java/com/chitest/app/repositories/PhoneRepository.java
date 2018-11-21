package com.chitest.app.repositories;

import com.chitest.app.entities.Phone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends CrudRepository<Phone, Integer> {

    Phone findByPhone(String phone);

}
