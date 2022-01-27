package io.github.igormateus.repertapp.service;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import io.github.igormateus.repertapp.exception.CustomException;
import io.github.igormateus.repertapp.model.AppUser;
import io.github.igormateus.repertapp.model.Band;
import io.github.igormateus.repertapp.repository.BandRepository;
import io.github.igormateus.repertapp.validation.BandValidation;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BandService {

    private final BandRepository bandRepository;
    private final BandValidation bandValidation;

    public Band create(@Valid Band band, AppUser user) {
        bandValidation.valideCreation(band);
        band.getMembers().add(user);

        return bandRepository.save(band);
    }

    public Page<Band> findAllByUser(AppUser user, Pageable page) {
        return bandRepository.findByMembers(user, page);
    }

    public Band findByIdAndUser(Long bandId, AppUser user) {
        Band band = bandRepository.findById(bandId)
                .orElseThrow(() -> new CustomException(String.format("Band id '%s' not found", bandId), HttpStatus.NOT_FOUND));
        
        if (!band.getMembers().stream().anyMatch(member -> member.getId() == user.getId()))
            throw new AccessDeniedException(String.format("You don't have access for Band '%s'", band.getName()));

        return band;
    }

    
}