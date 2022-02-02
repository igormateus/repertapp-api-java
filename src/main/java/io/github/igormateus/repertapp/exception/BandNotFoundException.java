package io.github.igormateus.repertapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BandNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public BandNotFoundException(String name) {
        super(String.format("There's no Band with the name '%s'.", name));
    }

    public BandNotFoundException(Long id) {
        super(String.format("There's no Band with the id '%d'.", id));
    }

}
