package io.github.igormateus.repertapp.service;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import io.github.igormateus.repertapp.dto.band.BandUpdateDTO;
import io.github.igormateus.repertapp.exception.BandDeleteNoSingleMemberException;
import io.github.igormateus.repertapp.exception.BandNotFoundException;
import io.github.igormateus.repertapp.exception.OnlyMemberBandException;
import io.github.igormateus.repertapp.exception.UserAlreadyExistsInTheBandException;
import io.github.igormateus.repertapp.exception.UserNotInBandException;
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
    private final UserService userService;

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
                .orElseThrow(() -> new BandNotFoundException(bandId));
        
        if (!band.getMembers().stream().anyMatch(member -> member.getId() == user.getId()))
            throw new AccessDeniedException(String.format("You don't have access for Band '%s'", band.getName()));

        return band;
    }

    public Band edit(Long bandId, BandUpdateDTO bandUpdate, AppUser user) {
        Band bandSaved = findByIdAndUser(bandId, user);
        
        bandValidation.valideUpdate(bandSaved, bandUpdate);

        bandSaved.setName(bandUpdate.getName());
        bandSaved.setDescription(bandUpdate.getDescription());

        return bandRepository.save(bandSaved);
    }

    public void delete(Long bandId, AppUser user) {
        Band band = findByIdAndUser(bandId, user);

        if (band.getMembers().size() > 1)
            throw new BandDeleteNoSingleMemberException(band.getName());

        bandRepository.delete(band);
    }

    public Band addMember(Long bandId, Long memberId, AppUser user) {
        Band band = findByIdAndUser(bandId, user);

        if (band.getMembers().stream().anyMatch(m -> m.getId() == memberId))
            throw new UserAlreadyExistsInTheBandException(memberId, band.getName());

        band.getMembers().add(userService.findOne(memberId));

        return bandRepository.save(band);
    }

    public Band removeMember(Long bandId, Long memberId, AppUser user) {
        Band band = findByIdAndUser(bandId, user);

        if (band.getMembers().size() == 1)
            throw new OnlyMemberBandException(band.getName());
        
        if (band.getMembers().stream().noneMatch(m -> m.getId() == memberId))
            throw new UserNotInBandException(memberId, band.getName());

        band.getMembers().remove(userService.findOne(memberId));

        return bandRepository.save(band);
    }

    
}
