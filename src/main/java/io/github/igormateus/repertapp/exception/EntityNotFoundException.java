package io.github.igormateus.repertapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public abstract class EntityNotFoundException extends GenericBusinessException {

	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String bandName) {
		super(String.format("Band '%s' must have only one member to be deleted", bandName));
	}

}
