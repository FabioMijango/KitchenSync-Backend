package org.torres.backendkitchen.Controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.torres.backendkitchen.Domain.DTO.Tables.TableCreateDTO;
import org.torres.backendkitchen.Domain.DTO.Tables.TableUpdateDTO;
import org.torres.backendkitchen.Domain.Entity.Tables;
import org.torres.backendkitchen.Exception.EntitiesException.EntityNotFoundException;
import org.torres.backendkitchen.Exception.EntitiesException.EntityNotValidException;
import org.torres.backendkitchen.Services.ServiceImplementation.TableServiceImpl;
import org.torres.backendkitchen.util.GenericResponse;

import java.util.List;

import static org.torres.backendkitchen.util.Constants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(KITCHEN + TABLE_CONTROLLER)
public class TableController {

    private final TableServiceImpl tableService;

    @PreAuthorize("hasAnyRole('MESERO', 'ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<GenericResponse> getAllTables() {
        List<Tables> tables = tableService.getAllTables();

        return GenericResponse.builder()
                .message("Tables retrieved successfully")
                .data(tables)
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<GenericResponse> createTable(@RequestBody @Valid TableCreateDTO tableDTO) {
        System.out.println("Creating table" );
        if (tableDTO.getNumber() < 0 ) {
            throw new EntityNotValidException("El número de mesa debe ser mayor a 0");
        }

        tableService.createTable(tableDTO);

        return GenericResponse.builder()
                .data(null)
                .message("Mesa creada exitosamente")
                .status(HttpStatus.CREATED)
                .build().buildResponse();
    }

    @GetMapping("{tableNumber}")
    public ResponseEntity<GenericResponse> getTableByNumber(@PathVariable("tableNumber") Integer tableNumber) {
        System.out.println("Retrieving table by tableNumber: " + tableNumber);
        Tables table = tableService.getTableByNumber(tableNumber);

        if (table == null) {
            throw new EntityNotFoundException("Table not found");
        }

        return GenericResponse.builder()
                .data(table)
                .message("Table retrieved successfully")
                .status(HttpStatus.OK)
                .build().buildResponse();
    }


    @PreAuthorize("hasAnyRole('MESERO', 'ADMINISTRADOR')")
    @PutMapping("/{tableNumber}")
    public ResponseEntity<GenericResponse> updateTableState(@PathVariable("tableNumber") Integer tableNumber,
                                                       @RequestBody @Valid TableUpdateDTO tableDTO) {

        tableService.updateTable(tableNumber, tableDTO);

        return GenericResponse.builder()
                .data(null)
                .message("Table state updated successfully")
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{tableNumber}")
    public ResponseEntity<GenericResponse> deleteTableByNumber(@PathVariable("tableNumber") Integer tableNumber) {
        System.out.println("Deleting table by tableNumber: " + tableNumber);
        tableService.deleteTableByNumber(tableNumber);

        return GenericResponse.builder()
                .data(null)
                .message("Table deleted successfully")
                .status(HttpStatus.OK)
                .build().buildResponse();
    }



}
