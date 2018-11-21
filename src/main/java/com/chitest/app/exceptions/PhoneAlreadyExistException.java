package com.chitest.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PhoneAlreadyExistException extends RuntimeException {
    public PhoneAlreadyExistException(String phoneName) {
        super("Contact with such  phone:" + phoneName + " already exist");
    }
}
