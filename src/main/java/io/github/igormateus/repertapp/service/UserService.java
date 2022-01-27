package io.github.igormateus.repertapp.service;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.igormateus.repertapp.dto.user.UserAuthResponseDTO;
import io.github.igormateus.repertapp.dto.user.UserUpdateDTO;
import io.github.igormateus.repertapp.exception.CustomException;
import io.github.igormateus.repertapp.model.AppUser;
import io.github.igormateus.repertapp.model.AppUserRole;
import io.github.igormateus.repertapp.repository.UserRepository;
import io.github.igormateus.repertapp.security.JwtTokenProvider;
import io.github.igormateus.repertapp.validation.UserValidation;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserValidation userValidation;

    public UserAuthResponseDTO signup(@Valid AppUser appUser) {
        userValidation.valideCreation(appUser);
            
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setAppUserRoles(new ArrayList<AppUserRole>(Arrays.asList(AppUserRole.ROLE_CLIENT)));
        
        AppUser user = userRepository.save(appUser);
        String jwtToken = jwtTokenProvider.createToken(appUser.getUsername(), appUser.getAppUserRoles());

        return new UserAuthResponseDTO(user, jwtToken);
    }

    public String signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getAppUserRoles());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public AppUser whoami(HttpServletRequest req) {
        return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    }

    public Page<AppUser> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public AppUser findOne(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND));
    }

    public AppUser edit(UserUpdateDTO userUpdate, HttpServletRequest req) {
        AppUser appUser = whoami(req);

        userValidation.valideUpdate(appUser, userUpdate);

        appUser.setUsername(userUpdate.getUsername());
        appUser.setName(userUpdate.getName());
        appUser.setEmail(userUpdate.getEmail());
        appUser.setBio(userUpdate.getBio());
        if (appUser.getPassword() != null) 
            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        return userRepository.save(appUser);
    }

    public String refresh(String username) {
        return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getAppUserRoles());
    }

}