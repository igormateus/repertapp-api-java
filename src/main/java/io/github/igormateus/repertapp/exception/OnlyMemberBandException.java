package io.github.igormateus.repertapp.exception;

public class OnlyMemberBandException extends GenericBusinessException {
    
    public OnlyMemberBandException(String bandName) {
        super(String.format("You are the only member of the band '%s'", bandName));
    }

}
