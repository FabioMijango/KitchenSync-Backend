package org.torres.backendkitchen.Services.ServiceImplementation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.torres.backendkitchen.Domain.DTO.Tables.TableCreateDTO;
import org.torres.backendkitchen.Domain.DTO.Tables.TableUpdateDTO;
import org.torres.backendkitchen.Domain.Entity.Order;
import org.torres.backendkitchen.Domain.Entity.Tables;
import org.torres.backendkitchen.Domain.Enum.TableState;
import org.torres.backendkitchen.Exception.EntitiesException.EntityDuplicatedException;
import org.torres.backendkitchen.Exception.EntitiesException.EntityNotFoundException;
import org.torres.backendkitchen.Repository.iOrderRepository;
import org.torres.backendkitchen.Repository.iTableRepository;
import org.torres.backendkitchen.Services.iTableService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements iTableService {

    private final iTableRepository tableRepository;
    private final iOrderRepository orderRepository;

    @Override
    public List<Tables> getAllTables() {

        return tableRepository.findAll();
    }

    @Override
    public Tables getTableByNumber(Integer number) {

        return tableRepository.findByNumber(number).orElseThrow(
                () -> new EntityNotFoundException("Table not found.")
        );
    }


    @Override
    public void createTable(TableCreateDTO tableDTO) {
        if (tableRepository.findByNumber(tableDTO.getNumber()).isPresent()) {
            throw new EntityDuplicatedException("Table with number " + tableDTO.getNumber() + " already exists.");
        }

        Tables table = Tables.builder().
                number(tableDTO.getNumber()).
                state(TableState.AVAILABLE)
                .build();

        tableRepository.save(table);
    }

    @Override
    public void updateTable(Integer tableNumber, TableUpdateDTO tableDTO) {
        Tables table = tableRepository.findByNumber(tableNumber).orElseThrow(
                () -> new EntityNotFoundException("Table not found.")
        );


        table.setState(TableState.valueOf(tableDTO.getState().toUpperCase()));

        tableRepository.save(table);
    }


    @Override
    public void deleteTableByNumber(Integer number) {
        Tables table = tableRepository.findByNumber(number).orElseThrow(
                () -> new EntityNotFoundException("Table not found.")
        );

        // Eliminar primero todas las órdenes asociadas a esta mesa
        List<Order> associatedOrders = orderRepository.findByTable(table);
        if (!associatedOrders.isEmpty()) {
            orderRepository.deleteAll(associatedOrders);
        }
        tableRepository.delete(table);
    }

    @Override
    public void updateTableState(Integer tableNumber, String newState) {
        Tables table = tableRepository.findByNumber(tableNumber).orElseThrow(
                () -> new EntityNotFoundException("Table not found.")
        );
        table.setState(TableState.valueOf(newState.toUpperCase()));

    }
}
