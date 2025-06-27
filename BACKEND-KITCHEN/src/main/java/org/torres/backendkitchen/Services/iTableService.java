package org.torres.backendkitchen.Services;

import org.torres.backendkitchen.Domain.DTO.Tables.TableCreateDTO;
import org.torres.backendkitchen.Domain.DTO.Tables.TableUpdateDTO;
import org.torres.backendkitchen.Domain.Entity.Tables;

import java.util.List;

public interface iTableService {

    /**
     * Retrieves all tables from the database.
     *
     * @return a list of all tables.
     */
    List<Tables> getAllTables();

    /**
     * Retrieves a table by its number.
     *
     * @param number the table number to search for.
     * @return the table with the specified number, or null if not found.
     */
    Tables getTableByNumber(Integer number);

    /**
     * Creates a new table in the database.
     *
     * @param tableDTO the data transfer object containing the table information.
     */
    void createTable(TableCreateDTO tableDTO);


    /*
     * Updates an existing table in the database.
     *
     * @param id      the ID of the table to be updated.
     * @param tableDTO the data transfer object containing the updated table information.
     */
    void updateTable(Integer tableNumber, TableUpdateDTO tableDTO);

    /**
     * Deletes a table from the database by its number.
     *
     * @param number the number of the table to be deleted.
     */
    void deleteTableByNumber(Integer number);


    /**
     * Updates the state of a table in the database.
     *
     * @param id    the ID of the table to be updated.
     * @param state the new state to set for the table.
     */
    void updateTableState(Integer tableNumber, String state);


    /*
    TODO: Will need methods for handling table reservations, and reservation cancellations?
     */

}
