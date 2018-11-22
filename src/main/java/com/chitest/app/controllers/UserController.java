package com.chitest.app.controllers;

import com.chitest.app.entities.User;
import com.chitest.app.exceptions.UserAlreadyExistException;
import com.chitest.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(path = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Map registerUser(@RequestBody Map<String, ?> input) {

        String login = (String) input.get("login");
        String pass = (String) input.get("password");
        User user = userService.findByLogin(login);
        if(user != null){
            throw new UserAlreadyExistException(user.getLogin());
        } else {
            String encoding = Base64.getEncoder().encodeToString((login.concat(":").concat(pass).concat(String.valueOf(new Date()))).getBytes());

            user = new User();
            user.setLogin(login);

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String hashedUserPass = bCryptPasswordEncoder.encode(pass);
            user.setPassword(hashedUserPass);

            userService.save(user);

            return Collections.singletonMap("token", encoding);
        }
    }

}
