package io.github.igormateus.repertapp.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreateDTO {

    @NotBlank
    @Size(min = 3, max = 255)
    private String username;
  
    @NotBlank
    @Size(min = 8, max = 255)
    private String password;
  
    @Size(min = 3, max = 255)
    private String name;
    
    @Email
    private String email;
  
    @Size(max = 250)
    private String bio;
}