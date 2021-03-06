package io.github.igormateus.repertapp.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import io.github.igormateus.repertapp.dto.user.UserUpdateDTO;
import io.github.igormateus.repertapp.exception.DuplicateValidationException;
import io.github.igormateus.repertapp.model.AppUser;
import io.github.igormateus.repertapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserValidation {

    private final UserRepository userRepository;

    public void valideCreation(AppUser appUser) {
        List<String> errors = new ArrayList<String>();

        if (userRepository.existsByUsername(appUser.getUsername()))
            errors.add(String.format("Username '%s' is already in use", appUser.getUsername()));

        if (appUser.getEmail() != null && userRepository.existsByEmail(appUser.getEmail()))
            errors.add(String.format("Email '%s' is already in use", appUser.getEmail()));

        if (appUser.getName() != null && userRepository.existsByName(appUser.getName()))
            errors.add(String.format("Name '%s' is already in use", appUser.getName()));

        if (errors.size() > 0)
            throw new DuplicateValidationException(String.join("; ", errors));
    }

    public void valideUpdate(AppUser userSaved, UserUpdateDTO newUser) {
        List<String> errors = new ArrayList<String>();

        if (!userSaved.getUsername().equals(newUser.getUsername()) 
                && userRepository.existsByUsername(newUser.getUsername()))
            errors.add(String.format("Username '%s' is already in use", newUser.getUsername()));

        if (newUser.getEmail() != null 
                && userSaved.getEmail() != newUser.getEmail()
                && userRepository.existsByEmail(newUser.getEmail()))
            errors.add(String.format("Email '%s' is already in use", newUser.getEmail()));

        if (newUser.getName() != null 
                && userSaved.getName() != newUser.getName()
                && userRepository.existsByName(newUser.getName()))
            errors.add(String.format("Name '%s' is already in use", newUser.getName()));

        if (errors.size() > 0)
            throw new DuplicateValidationException(String.join("; ", errors));
    }
}
