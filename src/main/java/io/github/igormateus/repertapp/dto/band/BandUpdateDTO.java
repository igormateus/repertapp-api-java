package io.github.igormateus.repertapp.dto.band;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class BandUpdateDTO {
    
    @NotBlank @Size(min = 5, max = 255)
    private String name;

    @Size(max = 255)
    private String description;
}
