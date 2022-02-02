package io.github.igormateus.repertapp.exception;

public class UserAlreadyExistsInTheBandException extends GenericBusinessException {

    public UserAlreadyExistsInTheBandException(Long userId, String bandName) {
        super(String.format("User id '%d' already exists in the band '%s'", userId, bandName));
    }
    
}
