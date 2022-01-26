package io.github.igormateus.repertapp.service;

import java.util.ArrayList;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.igormateus.repertapp.dto.user.UserAuthResponseDTO;
import io.github.igormateus.repertapp.model.AppUser;
import io.github.igormateus.repertapp.repository.UserRepository;
import io.github.igormateus.repertapp.security.JwtTokenProvider;
import io.github.igormateus.repertapp.util.TokenCreator;
import io.github.igormateus.repertapp.util.UserCreator;
import io.github.igormateus.repertapp.validation.UserValidation;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private UserValidation userValidationMock;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @Mock
    private JwtTokenProvider jwtTokenProviderMock;

    @BeforeEach
    public void setUp() {
        BDDMockito.when(userRepositoryMock.save(ArgumentMatchers.any(AppUser.class)))
                .thenReturn(UserCreator.createValid());

        BDDMockito.when(passwordEncoderMock.encode(ArgumentMatchers.any(String.class)))
                .thenReturn(UserCreator.createValid().getPassword());

        BDDMockito.when(jwtTokenProviderMock.createToken(ArgumentMatchers.any(String.class), ArgumentMatchers.any(ArrayList.class)))
                .thenReturn(TokenCreator.createValid());

        userValidationMock = new UserValidation(userRepositoryMock);
    }

    @Test
    @DisplayName("signup returns UserAuthResponseDTO when successful")
    void signup_returnsUserAuthResponseDTO_whenSuccessful() {
        AppUser user = UserCreator.createToBeSaved();

        UserAuthResponseDTO response = userService.signup(user);

        AppUser userUT = response.getUser();
        AppUser userCompare = UserCreator.createValid();

        Assertions.assertThat(userUT.getId()).isEqualTo(userCompare.getId());
        Assertions.assertThat(userUT.getUsername()).isEqualTo(userCompare.getUsername());
        Assertions.assertThat(userUT.getName()).isEqualTo(userCompare.getName());
        Assertions.assertThat(userUT.getEmail()).isEqualTo(userCompare.getEmail());
        Assertions.assertThat(response.getJwtToken()).isInstanceOf(String.class);
    }
}
