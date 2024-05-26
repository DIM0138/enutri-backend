package br.com.enutri.controller;

import br.com.enutri.exception.DeleteOperationException;
import br.com.enutri.exception.DuplicateResourceException;
import br.com.enutri.exception.ErrorMessage;
import br.com.enutri.exception.ResourceNotFoundException;
import br.com.enutri.exception.UnauthorizedAccessException;
import jakarta.validation.ConstraintViolationException;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage());
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorMessage> unauthorizedAccessException(UnauthorizedAccessException ex) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage());
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DeleteOperationException.class)
    public ResponseEntity<ErrorMessage> deleteOperationException(DeleteOperationException ex) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage());
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorMessage> duplicateResourceException(DuplicateResourceException ex) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                ex.getMessage());
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> onConstraintValidationException(ConstraintViolationException e) {
        Map<String, String> erros = new HashMap<>();

        e.getConstraintViolations().forEach((violation) -> {
            erros.put(violation.getPropertyPath().toString(), violation.getMessage());
        });
        
        return erros;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String campo = ((FieldError) error).getField();
            String erro = error.getDefaultMessage();
            errors.put(campo, erro);
        });
        return errors;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> illegalArgumentException(IllegalArgumentException ex) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage());
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage());
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleAllExceptions(Exception ex){
    ErrorMessage message = new ErrorMessage(
    HttpStatus.INTERNAL_SERVER_ERROR.value(),
    "Ocorreu um erro."
    );
    return new ResponseEntity<ErrorMessage>(message,
    HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
