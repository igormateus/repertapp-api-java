package io.github.igormateus.repertapp.exception;

public class InvalidJwtTokenException extends RuntimeException {

    public InvalidJwtTokenException(String message) {
        super(message);
    }
    
}
