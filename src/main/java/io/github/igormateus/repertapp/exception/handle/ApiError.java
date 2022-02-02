package io.github.igormateus.repertapp.exception.handle;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class ApiError {

    private Integer status;
    private String title;
    private String detail;

    private String userMessage;
    private OffsetDateTime timestamp;

    private List<Object> errorObjects;

    @Getter
    @Builder
    public static class Object {
        
        private String name;
        private String userMessage;
    }

}
