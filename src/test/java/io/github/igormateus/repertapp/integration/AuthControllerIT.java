package io.github.igormateus.repertapp.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import io.github.igormateus.repertapp.dto.user.UserAuthDTO;
import io.github.igormateus.repertapp.dto.user.UserCreateDTO;
import io.github.igormateus.repertapp.dto.user.UserResponseDTO;
import io.github.igormateus.repertapp.service.UserService;
import io.github.igormateus.repertapp.util.UserAuthDTOCreator;
import io.github.igormateus.repertapp.util.UserCreateDTOCreator;
import io.github.igormateus.repertapp.util.UserCreator;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AuthControllerIT {
    
    @Autowired
    private TestRestTemplate testRestTemplate;
    
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("signup returns user and jwttoken when successful")
    void signup_returnsUserAndJWTtoken_whenSuccessful() {

        UserCreateDTO userCreateDTO = UserCreateDTOCreator.createValid();

        ResponseEntity<UserResponseDTO> authResponseEntity = testRestTemplate.postForEntity(
            "/auth/signup",
            userCreateDTO,
            UserResponseDTO.class
        );

        Assertions.assertThat(authResponseEntity).isNotNull();
        Assertions.assertThat(authResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(authResponseEntity.getHeaders().getFirst("Authorization")).isNotNull();
        Assertions.assertThat(authResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(authResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("signin returns nocontent on body with token in headers when successful")
    void signin_returnsNoContentOnBodyWithTokenInHeaders_whenSuccessful() {

        userService.signup(UserCreator.createToBeSaved());

        UserAuthDTO userAuthDTO = UserAuthDTOCreator.createValid();

        ResponseEntity<Void> authResponseEntity = testRestTemplate.postForEntity(
            "/auth/signin",
            userAuthDTO,
            Void.class
        );

        Assertions.assertThat(authResponseEntity).isNotNull();
        Assertions.assertThat(authResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(authResponseEntity.getHeaders().getFirst("Authorization")).isNotNull();
    }
}
