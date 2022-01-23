package io.github.igormateus.repertapp.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.igormateus.repertapp.domain.model.User;
import io.github.igormateus.repertapp.domain.repository.UserRespository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRespository userRespository;
    private final PasswordEncoder encoder;

    @GetMapping
    public ResponseEntity<List<User>> listAll() {

        return ResponseEntity.ok(userRespository.findAll());
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {

        user.setPassword(encoder.encode(user.getPassword()));

        return ResponseEntity.ok(userRespository.save(user));
    }
}
