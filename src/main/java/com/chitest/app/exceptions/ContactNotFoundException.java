package com.chitest.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContactNotFoundException extends RuntimeException {

    public ContactNotFoundException(int contactId) {
        super("Contact with such id:" + contactId + " doesn't exist");
    }

}
