package com.chitest.app.services;


import com.chitest.app.entities.Email;
import com.chitest.app.entities.Phone;
import com.chitest.app.repositories.EmailRepository;
import com.chitest.app.repositories.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PhoneService {

    private final PhoneRepository phoneRepository;

    @Autowired
    public PhoneService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public Set<Phone> findDuplicatesPhones(Set<Phone> listContainingDuplicates)
    {
        final Set<Phone> setToReturn = new HashSet<>();
        final Set<String> set1 = new HashSet<>();

        for (Phone yourInt : listContainingDuplicates)
        {
            if (!set1.add(yourInt.getPhone()))
            {
                setToReturn.add(yourInt);
            }
        }
        return setToReturn;
    }

    public Phone findByPhone(String phone) {
        return phoneRepository.findByPhone(phone);
    }

    public Phone save(Phone phone) {
        return phoneRepository.save(phone);
    }

    public void delete(Phone phone){
        phoneRepository.delete(phone);
    }
}
