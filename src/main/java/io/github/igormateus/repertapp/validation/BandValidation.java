package io.github.igormateus.repertapp.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import io.github.igormateus.repertapp.dto.band.BandUpdateDTO;
import io.github.igormateus.repertapp.exception.DuplicateValidationException;
import io.github.igormateus.repertapp.model.Band;
import io.github.igormateus.repertapp.repository.BandRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BandValidation {

    private final BandRepository bandRepository;

    public void valideCreation(Band band) {
        List<String> errors = new ArrayList<String>();

        if (bandRepository.existsByName(band.getName()))
            errors.add(String.format("Band name '%s' is already in use", band.getName()));

        if (errors.size() > 0)
            throw new DuplicateValidationException(String.join("; ", errors));
    }

    public void valideUpdate(Band bandSaved, BandUpdateDTO bandUpdate) {
        List<String> errors = new ArrayList<String>();

        if (!bandSaved.getName().equals(bandUpdate.getName()) 
                && bandRepository.existsByName(bandUpdate.getName()))
            errors.add(String.format("Username '%s' is already in use", bandUpdate.getName()));
        
        if (errors.size() > 0)
            throw new DuplicateValidationException(String.join("; ", errors));
    }
}
