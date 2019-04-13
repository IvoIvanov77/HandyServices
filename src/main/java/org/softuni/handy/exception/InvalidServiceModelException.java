package org.softuni.handy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidServiceModelException extends RuntimeException{

    public InvalidServiceModelException(String message) {
        super(message);
    }
}
