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
import io.github.igormateus.repertapp.exception.handle.ApiError;
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
    @DisplayName("signup returns an error when null username was sent")
    void signup_returnsAnError_whenNullUsernameWasSent() {
        UserCreateDTO userCreateDTO = UserCreateDTOCreator.createValid();
        userCreateDTO.setUsername(null);

        ResponseEntity<ApiError> authResponseEntity = testRestTemplate.postForEntity(
            "/auth/signup",
            userCreateDTO,
            ApiError.class
        );

        Assertions.assertThat(authResponseEntity).isNotNull();
        Assertions.assertThat(authResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(authResponseEntity.getHeaders().getFirst("Authorization")).isNull();
        Assertions.assertThat(authResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(authResponseEntity.getBody().getDetail()).isEqualTo("One or more fields are invalid or missing. Please, make sure you're sending the data according the API standards and try again.");
        Assertions.assertThat(authResponseEntity.getBody().getTitle()).isEqualTo("Invalid Data");
        Assertions.assertThat(authResponseEntity.getBody().getErrorObjects().get(0).getName()).isEqualTo("username");
        Assertions.assertThat(authResponseEntity.getBody().getErrorObjects().get(0).getUserMessage()).isEqualTo("é obrigatório");
    }

    @Test
    @DisplayName("signup returns an error when invalid username was sent")
    void signup_returnsAnError_whenInvalidUsernameWasSent() {
        UserCreateDTO userCreateDTO = UserCreateDTOCreator.createValid();
        userCreateDTO.setUsername("bi");

        ResponseEntity<ApiError> authResponseEntity = testRestTemplate.postForEntity(
            "/auth/signup",
            userCreateDTO,
            ApiError.class
        );

        Assertions.assertThat(authResponseEntity).isNotNull();
        Assertions.assertThat(authResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(authResponseEntity.getHeaders().getFirst("Authorization")).isNull();
        Assertions.assertThat(authResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(authResponseEntity.getBody().getDetail()).isEqualTo("One or more fields are invalid or missing. Please, make sure you're sending the data according the API standards and try again.");
        Assertions.assertThat(authResponseEntity.getBody().getTitle()).isEqualTo("Invalid Data");
        Assertions.assertThat(authResponseEntity.getBody().getErrorObjects().get(0).getName()).isEqualTo("username");
        Assertions.assertThat(authResponseEntity.getBody().getErrorObjects().get(0).getUserMessage()).isEqualTo("deve ter no mínimo 3 e no máximo 255 caracteres");
    }

    @Test
    @DisplayName("signup returns an error when null password was sent")
    void signup_returnsAnError_whenNullPasswordWasSent() {
        UserCreateDTO userCreateDTO = UserCreateDTOCreator.createValid();
        userCreateDTO.setPassword(null);

        ResponseEntity<ApiError> authResponseEntity = testRestTemplate.postForEntity(
            "/auth/signup",
            userCreateDTO,
            ApiError.class
        );

        Assertions.assertThat(authResponseEntity).isNotNull();
        Assertions.assertThat(authResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(authResponseEntity.getHeaders().getFirst("Authorization")).isNull();
        Assertions.assertThat(authResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(authResponseEntity.getBody().getDetail()).isEqualTo("One or more fields are invalid or missing. Please, make sure you're sending the data according the API standards and try again.");
        Assertions.assertThat(authResponseEntity.getBody().getTitle()).isEqualTo("Invalid Data");
        Assertions.assertThat(authResponseEntity.getBody().getErrorObjects().get(0).getName()).isEqualTo("password");
        Assertions.assertThat(authResponseEntity.getBody().getErrorObjects().get(0).getUserMessage()).isEqualTo("é obrigatório");
    }

    @Test
    @DisplayName("signup returns an error when invalid password was sent")
    void signup_returnsAnError_whenInvalidPasswordWasSent() {
        UserCreateDTO userCreateDTO = UserCreateDTOCreator.createValid();
        userCreateDTO.setPassword("invalid");

        ResponseEntity<ApiError> authResponseEntity = testRestTemplate.postForEntity(
            "/auth/signup",
            userCreateDTO,
            ApiError.class
        );

        Assertions.assertThat(authResponseEntity).isNotNull();
        Assertions.assertThat(authResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(authResponseEntity.getHeaders().getFirst("Authorization")).isNull();
        Assertions.assertThat(authResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(authResponseEntity.getBody().getDetail()).isEqualTo("One or more fields are invalid or missing. Please, make sure you're sending the data according the API standards and try again.");
        Assertions.assertThat(authResponseEntity.getBody().getTitle()).isEqualTo("Invalid Data");
        Assertions.assertThat(authResponseEntity.getBody().getErrorObjects().get(0).getName()).isEqualTo("password");
        Assertions.assertThat(authResponseEntity.getBody().getErrorObjects().get(0).getUserMessage()).isEqualTo("deve ter no mínimo 8 e no máximo 255 caracteres");
    }
    
    @Test
    @DisplayName("signup returns an error when invalid name was sent")
    void signup_returnsAnError_whenInvalidNameWasSent() {
        UserCreateDTO userCreateDTO = UserCreateDTOCreator.createValid();
        userCreateDTO.setName("19");

        ResponseEntity<ApiError> authResponseEntity = testRestTemplate.postForEntity(
            "/auth/signup",
            userCreateDTO,
            ApiError.class
        );

        Assertions.assertThat(authResponseEntity).isNotNull();
        Assertions.assertThat(authResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(authResponseEntity.getHeaders().getFirst("Authorization")).isNull();
        Assertions.assertThat(authResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(authResponseEntity.getBody().getDetail()).isEqualTo("One or more fields are invalid or missing. Please, make sure you're sending the data according the API standards and try again.");
        Assertions.assertThat(authResponseEntity.getBody().getTitle()).isEqualTo("Invalid Data");
        Assertions.assertThat(authResponseEntity.getBody().getErrorObjects().get(0).getName()).isEqualTo("name");
        Assertions.assertThat(authResponseEntity.getBody().getErrorObjects().get(0).getUserMessage()).isEqualTo("deve ter no mínimo 3 e no máximo 255 caracteres");
    }

    @Test
    @DisplayName("signup returns an error when invalid email was sent")
    void signup_returnsAnError_whenInvalidEmailWasSent() {
        UserCreateDTO userCreateDTO = UserCreateDTOCreator.createValid();
        userCreateDTO.setEmail("mail.com.br");

        ResponseEntity<ApiError> authResponseEntity = testRestTemplate.postForEntity(
            "/auth/signup",
            userCreateDTO,
            ApiError.class
        );

        Assertions.assertThat(authResponseEntity).isNotNull();
        Assertions.assertThat(authResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(authResponseEntity.getHeaders().getFirst("Authorization")).isNull();
        Assertions.assertThat(authResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(authResponseEntity.getBody().getDetail()).isEqualTo("One or more fields are invalid or missing. Please, make sure you're sending the data according the API standards and try again.");
        Assertions.assertThat(authResponseEntity.getBody().getTitle()).isEqualTo("Invalid Data");
        Assertions.assertThat(authResponseEntity.getBody().getErrorObjects().get(0).getName()).isEqualTo("email");
        Assertions.assertThat(authResponseEntity.getBody().getErrorObjects().get(0).getUserMessage()).isEqualTo("deve ser um e-mail válido");
    }

    @Test
    @DisplayName("signup returns an error when invalid bio was sent")
    void signup_returnsAnError_whenInvalidBioWasSent() {
        UserCreateDTO userCreateDTO = UserCreateDTOCreator.createValid();
        userCreateDTO.setBio(
            "0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789" + 
            "0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789" + 
            "0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789");

        ResponseEntity<ApiError> authResponseEntity = testRestTemplate.postForEntity(
            "/auth/signup",
            userCreateDTO,
            ApiError.class
        );

        Assertions.assertThat(authResponseEntity).isNotNull();
        Assertions.assertThat(authResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(authResponseEntity.getHeaders().getFirst("Authorization")).isNull();
        Assertions.assertThat(authResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(authResponseEntity.getBody().getDetail()).isEqualTo("One or more fields are invalid or missing. Please, make sure you're sending the data according the API standards and try again.");
        Assertions.assertThat(authResponseEntity.getBody().getTitle()).isEqualTo("Invalid Data");
        Assertions.assertThat(authResponseEntity.getBody().getErrorObjects().get(0).getName()).isEqualTo("bio");
        Assertions.assertThat(authResponseEntity.getBody().getErrorObjects().get(0).getUserMessage()).isEqualTo("deve ter no mínimo 0 e no máximo 250 caracteres");
    }

    @Test
    @DisplayName("signup returns an error when username already in use")
    void signup_returnsAnError_whenUsernameAlreadyInUse() {
        userService.signup(UserCreator.createToBeSaved());
        
        UserCreateDTO newUser = UserCreateDTOCreator.createValid();
        newUser.setEmail("new_email@mail.com.br");
        newUser.setName("new_name");

        ResponseEntity<ApiError> authResponseEntity = testRestTemplate.postForEntity(
            "/auth/signup",
            newUser,
            ApiError.class
        );

        Assertions.assertThat(authResponseEntity).isNotNull();
        Assertions.assertThat(authResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(authResponseEntity.getHeaders().getFirst("Authorization")).isNull();
        Assertions.assertThat(authResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(authResponseEntity.getBody().getDetail()).isEqualTo("Username 'username_test' is already in use");
        Assertions.assertThat(authResponseEntity.getBody().getTitle()).isEqualTo("Business Exception");
    }

    @Test
    @DisplayName("signup returns an error when name already in use")
    void signup_returnsAnError_whenNameAlreadyInUse() {
        userService.signup(UserCreator.createToBeSaved());
        
        UserCreateDTO newUser = UserCreateDTOCreator.createValid();
        newUser.setEmail("new_email@mail.com.br");
        newUser.setUsername("new_username");

        ResponseEntity<ApiError> authResponseEntity = testRestTemplate.postForEntity(
            "/auth/signup",
            newUser,
            ApiError.class
        );

        Assertions.assertThat(authResponseEntity).isNotNull();
        Assertions.assertThat(authResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(authResponseEntity.getHeaders().getFirst("Authorization")).isNull();
        Assertions.assertThat(authResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(authResponseEntity.getBody().getDetail()).isEqualTo("Name 'name_test' is already in use");
        Assertions.assertThat(authResponseEntity.getBody().getTitle()).isEqualTo("Business Exception");
    }

    @Test
    @DisplayName("signup returns an error when email already in use")
    void signup_returnsAnError_whenEmailAlreadyInUse() {
        userService.signup(UserCreator.createToBeSaved());
        
        UserCreateDTO newUser = UserCreateDTOCreator.createValid();
        newUser.setName("new_name");
        newUser.setUsername("new_username");

        ResponseEntity<ApiError> authResponseEntity = testRestTemplate.postForEntity(
            "/auth/signup",
            newUser,
            ApiError.class
        );

        Assertions.assertThat(authResponseEntity).isNotNull();
        Assertions.assertThat(authResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(authResponseEntity.getHeaders().getFirst("Authorization")).isNull();
        Assertions.assertThat(authResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(authResponseEntity.getBody().getDetail()).isEqualTo("Email 'test@email.com' is already in use");
        Assertions.assertThat(authResponseEntity.getBody().getTitle()).isEqualTo("Business Exception");
    }

    @Test
    @DisplayName("signin returns no content on body with token in headers when successful")
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
