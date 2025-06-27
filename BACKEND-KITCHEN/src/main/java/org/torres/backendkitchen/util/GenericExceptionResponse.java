package org.torres.backendkitchen.util;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericExceptionResponse {
    private String message;
    private String error;
    private String path;
    private String method;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    @Builder.Default
    @JsonIgnore
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public ResponseEntity<GenericExceptionResponse> buildResponse() {
        return ResponseEntity.status(status).body(this);
    }
}
