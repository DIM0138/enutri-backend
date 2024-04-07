package br.com.enutri.controller;

import br.com.enutri.exception.DeleteOperationException;
import br.com.enutri.exception.DuplicateResourceException;
import br.com.enutri.exception.ErrorMessage;
import br.com.enutri.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DeleteOperationException.class)
    public ResponseEntity<ErrorMessage> deleteOperationException(DeleteOperationException ex){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage()
        );
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorMessage> duplicateResourceException(DuplicateResourceException ex){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                ex.getMessage()
        );
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.CONFLICT);
    }

    // TODO: Implementar
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<ErrorMessage> dataIntegrityViolationException(DataIntegrityViolationException ex){
//        String ex_message = "Erro de integridade de dados: ";
//        if (ex.getCause() instanceof ConstraintViolationException) {
//            ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex.getCause();
//            ex_message += "Violação de restrição - " + constraintViolationException.getConstraintName();
//        } else {
//            ex_message += ex.getMostSpecificCause().getMessage();
//        }
//        ErrorMessage message = new ErrorMessage(
//                HttpStatus.CONFLICT.value(),
//                ex_message
//        );
//        return new ResponseEntity<ErrorMessage>(message, HttpStatus.CONFLICT);
//    }
}
