package io.github.igormateus.repertapp.repository;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import io.github.igormateus.repertapp.model.AppUser;
import io.github.igormateus.repertapp.util.UserCreator;

@DataJpaTest
@DisplayName("Tests for UserRepository")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("save persists user when Successful")
    void save_PersistUser_WhenSuccessful(){
        AppUser newUser = UserCreator.createToBeSaved();

        var userSaved = userRepository.save(newUser);

        Assertions.assertThat(userSaved).isNotNull();
        Assertions.assertThat(userSaved.getId()).isNotNull();
        Assertions.assertThat(userSaved.getCreatedAt()).isNotNull();
        Assertions.assertThat(userSaved.getUpdatedAt()).isNotNull();
        Assertions.assertThat(userSaved.getUsername()).isEqualTo(newUser.getUsername());
        Assertions.assertThat(userSaved.getPassword()).isEqualTo(newUser.getPassword());
    }

    @Test
    @DisplayName("save throws error when user has no username")
    void save_throwsError_whenUserHasNoUsername(){
        AppUser newUser = UserCreator.createToBeSaved();
        newUser.setUsername(null);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
            .isThrownBy(() -> userRepository.save(newUser));
    }

    @Test
    @DisplayName("save throws error when user has no password")
    void save_throwsError_whenUserHasNoPassword(){
        AppUser newUser = UserCreator.createToBeSaved();
        newUser.setPassword(null);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
            .isThrownBy(() -> userRepository.save(newUser));
    }

    @Test
    @DisplayName("save update user when successful")
    void save_updateUser_whenSuccessful() {
        AppUser user = UserCreator.createToBeSaved();
        AppUser userSaved = userRepository.save(user);

        userSaved.setUsername("new_user_name_test");
        userSaved.setPassword("new_password_test");
        userSaved.setBio("this is a new bio");
        userSaved.setEmail("new@email.com");

        AppUser userUpdated = userRepository.save(userSaved);

        Assertions.assertThat(userUpdated)
                .isNotNull()
                .isEqualTo(userSaved);
    }

    @Test
    @DisplayName("existsByUsername returns true when successful")
    void existsByUsername_returnsTrue_whenSuccessful() {
        AppUser user = UserCreator.createToBeSaved();
        AppUser userSaved = userRepository.save(user);

        boolean result = userRepository.existsByUsername(userSaved.getUsername());

        Assertions.assertThat(result).isTrue();
    }

    @Test
    @DisplayName("existsByUsername returns false when successful")
    void existsByUsername_returnsFalse_whenSuccessful() {
        AppUser user = UserCreator.createToBeSaved();

        boolean result = userRepository.existsByUsername(user.getUsername());

        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("existsByEmail returns true when successful")
    void existsByEmail_returnsTrue_whenSuccessful() {
        AppUser user = UserCreator.createToBeSaved();
        AppUser userSaved = userRepository.save(user);

        boolean result = userRepository.existsByEmail(userSaved.getEmail());

        Assertions.assertThat(result).isTrue();
    }

    @Test
    @DisplayName("existsByEmail returns false when successful")
    void existsByEmail_returnsFalse_whenSuccessful() {
        AppUser user = UserCreator.createToBeSaved();

        boolean result = userRepository.existsByEmail(user.getEmail());

        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("existsByName returns true when successful")
    void existsByName_returnsTrue_whenSuccessful() {
        AppUser user = UserCreator.createToBeSaved();
        AppUser userSaved = userRepository.save(user);

        boolean result = userRepository.existsByName(userSaved.getName());

        Assertions.assertThat(result).isTrue();
    }

    @Test
    @DisplayName("existsByName returns false when successful")
    void existsByName_returnsFalse_whenSuccessful() {
        AppUser user = UserCreator.createToBeSaved();

        boolean result = userRepository.existsByName(user.getName());

        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("findByUsername returns user when successful")
    void findByUsername_returnsUser_whenSuccessful() {
        AppUser user = UserCreator.createToBeSaved();
        AppUser userSaved = userRepository.save(user);

        AppUser userFound = userRepository.findByUsername(userSaved.getUsername());

        Assertions.assertThat(userFound).isEqualTo(userSaved);
    }

    @Test
    @DisplayName("findByUsername returns null when successful")
    void findByUsername_returnsNull_whenSuccessful() {
        AppUser user = UserCreator.createToBeSaved();

        AppUser userFound = userRepository.findByUsername(user.getUsername());

        Assertions.assertThat(userFound).isNull();
    }
}
