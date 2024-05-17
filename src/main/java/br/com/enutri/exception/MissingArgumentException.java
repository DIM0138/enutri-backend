package br.com.enutri.exception;

public class MissingArgumentException extends RuntimeException {
    public MissingArgumentException() { }
    
    public MissingArgumentException(String message) {
        super(message);
    }
}
