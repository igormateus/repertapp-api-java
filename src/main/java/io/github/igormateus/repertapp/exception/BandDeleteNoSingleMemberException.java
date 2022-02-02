package io.github.igormateus.repertapp.exception;

public class BandDeleteNoSingleMemberException extends GenericBusinessException {

    private static final long serialVersionUID = 1L;

    public BandDeleteNoSingleMemberException(String name) {
        super(String.format("Band '%s' must have only one member to be deleted", name));
    }
    
}
