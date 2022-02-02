package io.github.igormateus.repertapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class DuplicateValidationException extends GenericBusinessException {

    public DuplicateValidationException(String message) {
        super(message);
    }
    
}
