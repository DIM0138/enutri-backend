package br.com.enutri.controller;

import br.com.enutri.exception.ErrorMessage;
import br.com.enutri.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }
}
