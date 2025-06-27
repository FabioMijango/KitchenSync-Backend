package org.torres.backendkitchen.Domain.DTO.Tables;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TableCreateDTO {
    @NotNull
    private Integer number; // Table number
}