package com.chitest.app.services;


import com.chitest.app.entities.Email;
import com.chitest.app.entities.User;
import com.chitest.app.repositories.EmailRepository;
import com.chitest.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class EmailService  {

    private final EmailRepository emailRepository;

    @Autowired
    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    public Set<Email> findDuplicatesEmails(Set<Email> listContainingDuplicates)
    {
        final Set<Email> setToReturn = new HashSet<>();
        final Set<String> set1 = new HashSet<>();

        for (Email yourInt : listContainingDuplicates)
        {
            if (!set1.add(yourInt.getEmail()))
            {
                setToReturn.add(yourInt);
            }
        }
        return setToReturn;
    }

    public Email findByEmail(String email) {
        return emailRepository.findByEmail(email);
    }

    public Email save(Email email) {
        return emailRepository.save(email);
    }
    public void delete(Email email){
        emailRepository.delete(email);
    }
}
