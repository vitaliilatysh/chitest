package com.chitest.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String contactName) {
        super("User with such name:" + contactName + " already exist");
    }
}
