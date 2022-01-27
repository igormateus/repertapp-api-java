package io.github.igormateus.repertapp.dto.user;

import java.util.Date;
import java.util.List;

import io.github.igormateus.repertapp.model.AppUserRole;
import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;
    
    private Date createdAt;

    private Date updatedAt;

    private String username;

    private String name;

    private String email;

    private String bio;
  
    List<AppUserRole> appUserRoles;
}