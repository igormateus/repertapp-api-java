package io.github.igormateus.repertapp.service;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.igormateus.repertapp.exception.CustomException;
import io.github.igormateus.repertapp.model.AppUser;
import io.github.igormateus.repertapp.model.AppUserRole;
import io.github.igormateus.repertapp.repository.UserRepository;
import io.github.igormateus.repertapp.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public String signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getAppUserRoles());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String signup(@Valid AppUser appUser) {
        if (userRepository.existsByUsername(appUser.getUsername()))
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
            
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setAppUserRoles(new ArrayList<AppUserRole>(new ArrayList<AppUserRole>(Arrays.asList(AppUserRole.ROLE_CLIENT))));
        userRepository.save(appUser);
        return jwtTokenProvider.createToken(appUser.getUsername(), appUser.getAppUserRoles());
    }

    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }

    public AppUser search(String username) {
        AppUser appUser = userRepository.findByUsername(username);
        if (appUser == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return appUser;
    }

    public AppUser whoami(HttpServletRequest req) {
        return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    }

    public String refresh(String username) {
        return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getAppUserRoles());
    }

}