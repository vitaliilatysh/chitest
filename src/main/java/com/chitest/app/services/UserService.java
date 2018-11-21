package com.chitest.app.services;


import com.chitest.app.entities.User;
import com.chitest.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public User findByLogin(String login){
        return userRepository.findByLogin(login);
    }
}
