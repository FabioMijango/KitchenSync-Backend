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
public class GenericResponse {
    private String message;
    private Object data;

    @Builder.Default
    private String timestamp = LocalDateTime.now().toString();

    @Builder.Default
    @JsonIgnore
    private HttpStatus status = HttpStatus.OK;

    public ResponseEntity<GenericResponse> buildResponse() {
        return ResponseEntity.status(status).body(this);
    }
}