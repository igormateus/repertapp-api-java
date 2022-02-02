package io.github.igormateus.repertapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String username) {
        super(String.format("There's no User with the username '%s'.", username));
    }

    public UserNotFoundException(Long id) {
        super(String.format("There's no User with the id '%d'.", id));
    }

}
