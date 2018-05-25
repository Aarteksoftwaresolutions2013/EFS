package com.johnhancock.efs.controller;

import com.jh.common.model.ExceptionModel;
import com.johnhancock.efs.exception.EFSBadRequestException;
import com.johnhancock.efs.exception.EFSInternalServerException;
import com.johnhancock.efs.exception.EFSNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class EFSResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EFSBadRequestException.class)
    public final ResponseEntity<ExceptionModel> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionModel exceptionResponse = getExceptionModel(String.valueOf(HttpStatus.BAD_GATEWAY.value()), ex, request);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(EFSNotFoundException.class)
    public final ResponseEntity<ExceptionModel> handleNotFoundException(EFSNotFoundException ex, WebRequest request) {
        ExceptionModel exceptionResponse = getExceptionModel(String.valueOf(HttpStatus.NOT_FOUND.value()), ex, request);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EFSInternalServerException.class)
    public final ResponseEntity<ExceptionModel> handleInternalServerErrorException(EFSInternalServerException ex, WebRequest request) {
        ExceptionModel exceptionResponse = getExceptionModel(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), ex, request);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ExceptionModel getExceptionModel(String httpCode, Exception ex, WebRequest request) {
        ExceptionModel exceptionResponse = new ExceptionModel();
        exceptionResponse.setHttpcode(httpCode);
        exceptionResponse.setMessage(ex.getMessage());
        exceptionResponse.setCode(httpCode);
        return exceptionResponse;
    }
}