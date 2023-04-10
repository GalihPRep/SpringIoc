package com.example.layer.layer0controller;

import javax.persistence.EntityNotFoundException;
import com.example.model.response.Failure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ControllerError {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerError.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Failure> handleEntityNotFound(EntityNotFoundException ex) {
        LOGGER.error("Entity not found", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Failure(HttpStatus.NOT_FOUND.name(),"404: ENTITY NOT FOUND", ex.getMessage()));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Failure> handleEmptyResultDataAccess(EmptyResultDataAccessException ex) {
        LOGGER.error("Entity not found", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Failure(HttpStatus.NOT_FOUND.name(),"404: ENTITY NOT FOUND", ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Failure> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        LOGGER.error("Data integrity violation", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Failure(HttpStatus.BAD_REQUEST.name(),"403: DATA INTEGRITY VIOLATION", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Failure> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        LOGGER.error("Method argument type mismatch", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Failure(HttpStatus.BAD_REQUEST.name(),"403: METHOD ARGUMENT TYPE MISMATCH", "invalid request parameter: "+ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Failure> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        LOGGER.error("Method argument not valid", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Failure(HttpStatus.BAD_REQUEST.name(),"403: METHOD ARGUMENT NOT VALID", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Failure> handleException(Exception ex) {
        LOGGER.error("Internal server error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Failure(HttpStatus.INTERNAL_SERVER_ERROR.name(),"505: INTERNAL SERVER ERROR", "an unexpected error occurred!"));
    }
}

