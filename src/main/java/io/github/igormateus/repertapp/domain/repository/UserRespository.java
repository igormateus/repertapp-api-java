package io.github.igormateus.repertapp.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.igormateus.repertapp.domain.model.User;

@Repository
public interface UserRespository extends JpaRepository<User, Long> {
    
    public Optional<User> findByLogin(String login);
}
