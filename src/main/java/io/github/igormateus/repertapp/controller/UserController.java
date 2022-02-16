package io.github.igormateus.repertapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.igormateus.repertapp.dto.user.UserResponseDTO;
import io.github.igormateus.repertapp.dto.user.UserUpdateDTO;
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

    // Edits user's data
    @PutMapping("/me")
    public UserResponseDTO editMe(@Valid @RequestBody UserUpdateDTO user, HttpServletRequest req) {
        return modelMapper.map(userService.edit(user, req), UserResponseDTO.class);
    }
}
