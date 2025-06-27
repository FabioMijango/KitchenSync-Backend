package org.torres.backendkitchen.Domain.DTO.Tables;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TableUpdateDTO {
    @NotBlank
    @NotNull
    @NotEmpty
    private String state;
}
