package io.github.igormateus.repertapp.dto.band;

import java.util.Date;
import java.util.List;

import io.github.igormateus.repertapp.dto.user.ProfileDTO;
import lombok.Data;

@Data
public class BandResponseDTO {
    private Long id;

    private Date createdAt;

    private Date updatedAt;

    private String name;

    private String description;

    private List<ProfileDTO> members;
}
