package io.github.igormateus.repertapp.exception;

public class UserNotInBandException extends GenericBusinessException {

    public UserNotInBandException(Long userId, String bandName) {
        super(String.format("User id '%d' is not a member of the band '%s'", userId, bandName));
    }
    
}
