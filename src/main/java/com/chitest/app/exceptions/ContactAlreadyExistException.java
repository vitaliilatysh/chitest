package com.chitest.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ResponseStatus(HttpStatus.CONFLICT)
public class ContactAlreadyExistException extends RuntimeException {
    public ContactAlreadyExistException(String contactName) {
        super("Contact with such  name:" + contactName + " already exist");
    }
}
