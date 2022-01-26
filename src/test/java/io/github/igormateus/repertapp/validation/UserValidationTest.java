package io.github.igormateus.repertapp.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.igormateus.repertapp.dto.user.UserUpdateDTO;
import io.github.igormateus.repertapp.exception.CustomException;
import io.github.igormateus.repertapp.model.AppUser;
import io.github.igormateus.repertapp.repository.UserRepository;
import io.github.igormateus.repertapp.util.UserCreator;

@ExtendWith(SpringExtension.class)
public class UserValidationTest {

    @InjectMocks
    private UserValidation userValidation;

    @Mock
    private UserRepository userRepositoryMock;

    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        BDDMockito.when(userRepositoryMock.existsByUsername(UserCreator.createToBeSaved().getUsername()))
                .thenReturn(false);

        BDDMockito.when(userRepositoryMock.existsByUsername("username_saved"))
                .thenReturn(true);

        BDDMockito.when(userRepositoryMock.existsByEmail(UserCreator.createToBeSaved().getEmail()))
                .thenReturn(false);

        BDDMockito.when(userRepositoryMock.existsByEmail("email_saved@email.com"))
                .thenReturn(true);

        BDDMockito.when(userRepositoryMock.existsByName(UserCreator.createToBeSaved().getName()))
                .thenReturn(false);

        BDDMockito.when(userRepositoryMock.existsByName("name_saved"))
                .thenReturn(true);

        userValidation = new UserValidation(userRepositoryMock);
        
        modelMapper = new ModelMapper();
    }
    
    @Test
    @DisplayName("valideCreation pass without errors when successful")
    void valideCreation_passWithoutError_whenSuccessful() {
        AppUser userToBeSaved = UserCreator.createToBeSaved();

        Assertions.assertThatNoException().isThrownBy(() -> userValidation.valideCreation(userToBeSaved));
    }

    @Test
    @DisplayName("valideCreation throws error when username already exists")
    void valideCreation_throwsError_whenUsernameAlmostExists() {
        AppUser userToBeSaved = UserCreator.createToBeSaved();
        userToBeSaved.setUsername("username_saved");

        Assertions.assertThatExceptionOfType(CustomException.class)
                .isThrownBy(() -> userValidation.valideCreation(userToBeSaved))
                .withMessage("Username 'username_saved' is already in use");
    }

    @Test
    @DisplayName("valideCreation throws error when email already exists")
    void valideCreation_throwsError_whenEmailAlmostExists() {
        AppUser userToBeSaved = UserCreator.createToBeSaved();
        userToBeSaved.setEmail("email_saved@email.com");

        Assertions.assertThatExceptionOfType(CustomException.class)
                .isThrownBy(() -> userValidation.valideCreation(userToBeSaved))
                .withMessage("Email 'email_saved@email.com' is already in use");
    }

    @Test
    @DisplayName("valideCreation throws error when name already exists")
    void valideCreation_throwsError_whenNameAlmostExists() {
        AppUser userToBeSaved = UserCreator.createToBeSaved();
        userToBeSaved.setName("name_saved");

        Assertions.assertThatExceptionOfType(CustomException.class)
                .isThrownBy(() -> userValidation.valideCreation(userToBeSaved))
                .withMessage("Name 'name_saved' is already in use");
    }

    @Test
    @DisplayName("valideUpdate pass without errors when successful")
    void valideUpdate_passWithoutError_whenSuccessful() {
        AppUser user = UserCreator.createToBeSaved();
        user.setName("new_name_test");
        user.setEmail("new_email_test");
        user.setUsername("new_username_test");

        AppUser userSaved = UserCreator.createToBeSaved();

        Assertions.assertThatNoException().isThrownBy(() -> 
                userValidation.valideUpdate(userSaved, modelMapper.map(user, UserUpdateDTO.class)));
    }

    @Test
    @DisplayName("valideUpdate pass without errors when name and email is null")
    void valideUpdate_passWithoutError_whenNameAndEmailIsNull() {
        AppUser user = UserCreator.createToBeSaved();
        user.setName(null);
        user.setEmail(null);
        user.setUsername("new_username_test");

        AppUser userSaved = UserCreator.createToBeSaved();

        Assertions.assertThatNoException().isThrownBy(() -> 
                userValidation.valideUpdate(userSaved, modelMapper.map(user, UserUpdateDTO.class)));
    }

    @Test
    @DisplayName("valideUpdate throws error when username already exists")
    void valideUpdate_throwsError_whenUsernameAlmostExists() {
        AppUser user = UserCreator.createToBeSaved();
        user.setUsername("username_saved");

        AppUser userSaved = UserCreator.createToBeSaved();

        Assertions.assertThatExceptionOfType(CustomException.class)
                .isThrownBy(() -> userValidation.valideUpdate(userSaved, modelMapper.map(user, UserUpdateDTO.class)))
                .withMessage("Username 'username_saved' is already in use");
    }

    @Test
    @DisplayName("valideUpdate throws error when email almost exists")
    void valideUpdate_throwsError_whenEmailAlmostExists() {
        AppUser user = UserCreator.createToBeSaved();
        user.setEmail("email_saved@email.com");

        AppUser userSaved = UserCreator.createToBeSaved();

        Assertions.assertThatExceptionOfType(CustomException.class)
                .isThrownBy(() -> userValidation.valideUpdate(userSaved, modelMapper.map(user, UserUpdateDTO.class)))
                .withMessage("Email 'email_saved@email.com' is already in use");
    }

    @Test
    @DisplayName("valideUpdate throws error when name almost exists")
    void valideUpdate_throwsError_whenNameAlmostExists() {
        AppUser user = UserCreator.createToBeSaved();
        user.setName("name_saved");

        AppUser userSaved = UserCreator.createToBeSaved();

        Assertions.assertThatExceptionOfType(CustomException.class)
                .isThrownBy(() -> userValidation.valideUpdate(userSaved, modelMapper.map(user, UserUpdateDTO.class)))
                .withMessage("Name 'name_saved' is already in use");
    }
}
