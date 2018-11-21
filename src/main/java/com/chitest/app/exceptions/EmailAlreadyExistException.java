package com.chitest.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException(String emailName) {
        super("Contact with such  email:" + emailName + " already exist");
    }
}
