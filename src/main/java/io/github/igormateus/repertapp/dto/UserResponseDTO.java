package io.github.igormateus.repertapp.dto;

import java.util.List;

import io.github.igormateus.repertapp.model.AppUserRole;
import lombok.Data;

@Data
public class UserResponseDTO {

    private Integer id;

    private String username;

    private String email;
  
    List<AppUserRole> appUserRoles;

}