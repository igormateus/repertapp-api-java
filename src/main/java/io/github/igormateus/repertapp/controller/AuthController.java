package io.github.igormateus.repertapp.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.igormateus.repertapp.dto.user.UserAuthDTO;
import io.github.igormateus.repertapp.dto.user.UserAuthResponseDTO;
import io.github.igormateus.repertapp.dto.user.UserCreateDTO;
import io.github.igormateus.repertapp.dto.user.UserResponseDTO;
import io.github.igormateus.repertapp.model.AppUser;
import io.github.igormateus.repertapp.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    // Creates user and returns his and its JWT token on header
    @PostMapping("/signup")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<UserResponseDTO> signup(@Valid @RequestBody UserCreateDTO user) {

        UserAuthResponseDTO response = userService.signup(modelMapper.map(user, AppUser.class));

        return ResponseEntity
                .ok()
                .header("Authorization", response.getJwtToken())
                .body(modelMapper.map(response.getUser(), UserResponseDTO.class));
    }
    
    // Authenticates user and returns its JWT token.
    @PostMapping("/signin")
    public ResponseEntity<Void> login(@Valid @RequestBody UserAuthDTO user) {

        return ResponseEntity
                .ok()
                .header("Authorization", userService.signin(user.getUsername(), user.getPassword()))
                .build();
    }
}
