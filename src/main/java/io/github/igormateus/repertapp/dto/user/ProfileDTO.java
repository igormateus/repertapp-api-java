package io.github.igormateus.repertapp.dto.user;

import lombok.Data;

@Data
public class ProfileDTO {

    private Long id;

    private String name;

    private String username;

    private String bio;
}
