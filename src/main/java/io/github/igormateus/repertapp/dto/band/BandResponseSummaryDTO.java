package io.github.igormateus.repertapp.dto.band;

import java.util.Date;

import lombok.Data;

@Data
public class BandResponseSummaryDTO {
    private Long id;

    private Date createdAt;

    private Date updatedAt;

    private String name;

    private String description;
}
