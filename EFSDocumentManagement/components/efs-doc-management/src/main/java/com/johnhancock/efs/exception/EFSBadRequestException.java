package com.johnhancock.efs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EFSBadRequestException extends RuntimeException{

    public EFSBadRequestException(String exception){
        super(exception);
    }
}