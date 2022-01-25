package io.github.igormateus.repertapp.dto.user;

import io.github.igormateus.repertapp.model.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAuthResponseDTO {

    private AppUser user;

    private String jwtToken;
}
