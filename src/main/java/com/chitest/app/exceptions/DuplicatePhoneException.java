package com.chitest.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicatePhoneException extends RuntimeException {

    public DuplicatePhoneException(String exception) {
        super(exception);
    }

}
