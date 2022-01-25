package io.github.igormateus.repertapp.controller;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.igormateus.repertapp.dto.user.ProfileDTO;
import io.github.igormateus.repertapp.model.AppUser;
import io.github.igormateus.repertapp.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Page<ProfileDTO>> findAll(Pageable pageable) {
        Page<AppUser> users = userService.findAll(pageable);

        return ResponseEntity
                .ok(users.map(user -> modelMapper.map(user, ProfileDTO.class)));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileDTO> findOne(@PathVariable Long userId) {
        return ResponseEntity
                .ok(modelMapper.map(userService.findOne(userId), ProfileDTO.class));
    }
    
}
