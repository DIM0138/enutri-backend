package br.com.enutri.exception;

public class DeleteOperationException extends RuntimeException{
    public DeleteOperationException() {
    }

    public DeleteOperationException(String message) {
        super(message);
    }
}
