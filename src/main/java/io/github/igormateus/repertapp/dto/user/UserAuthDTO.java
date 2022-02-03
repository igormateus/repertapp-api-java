package io.github.igormateus.repertapp.dto.user;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserAuthDTO {

    @NotBlank
    private String username;
  
    @NotBlank
    private String password;
}
