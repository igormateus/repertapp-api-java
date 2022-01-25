package io.github.igormateus.repertapp.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.igormateus.repertapp.model.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long> {

  boolean existsByUsername(String username);
  boolean existsByEmail(String email);
  boolean existsByName(String name);

  AppUser findByUsername(String username);

  @Transactional
  void deleteByUsername(String username);
}
