package com.johnhancock.efs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EFSNotFoundException extends RuntimeException{

    public EFSNotFoundException(String exception){
        super(exception);
    }
}
