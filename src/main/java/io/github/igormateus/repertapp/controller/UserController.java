package io.github.igormateus.repertapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.igormateus.repertapp.dto.UserAuthDTO;
import io.github.igormateus.repertapp.dto.UserDataDTO;
import io.github.igormateus.repertapp.dto.UserResponseDTO;
import io.github.igormateus.repertapp.model.AppUser;
import io.github.igormateus.repertapp.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    // Returns current user's data
    @GetMapping("/me")
    public UserResponseDTO whoami(HttpServletRequest req) {
        return modelMapper.map(userService.whoami(req), UserResponseDTO.class);
    }

    // Creates user and returns its JWT token
    @PostMapping("/signup")
    public String signup(@RequestBody UserDataDTO user) {
        return userService.signup(modelMapper.map(user, AppUser.class));
    }

    // Authenticates user and returns its JWT token.
    @PostMapping("/signin")
    public String login(@RequestBody UserAuthDTO user) {
        return userService.signin(user.getUsername(), user.getPassword());
    }
}
