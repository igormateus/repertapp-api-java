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
            .isThrownBy(() -> userRepository.save(newUser))
            ;
    }
}
