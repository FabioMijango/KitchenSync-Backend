package org.torres.backendkitchen.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.torres.backendkitchen.Domain.DTO.GenericResponse;
import org.torres.backendkitchen.Domain.DTO.Orders.OrderCreateDTO;
import org.torres.backendkitchen.Domain.DTO.Orders.OrderSummaryDTO;
import org.torres.backendkitchen.Domain.DTO.Orders.OrderUpdateDTO;
import org.torres.backendkitchen.Domain.Entity.Order;
import org.torres.backendkitchen.Services.iOrderService;

import java.util.UUID;

import static org.torres.backendkitchen.util.Constants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(KITCHEN+ORDER_CONTROLLER)
public class OrderController {
    private final iOrderService orderService;

    @PreAuthorize("hasAnyRole('MESERO', 'ADMINISTRADOR')")
    @PostMapping(CREATE)
    public ResponseEntity<GenericResponse> createOrder(@RequestBody @Valid OrderCreateDTO order) {
        try{
            orderService.createOrder(order);
            return GenericResponse.builder()
                    .message("OrderCreated")
                    .data(null)
                    .status(HttpStatus.CREATED)
                    .build()
                    .buildResponse();

        }catch (Exception e){
            return GenericResponse
                    .builder()
                    .message("OrderCreationFailed: " + e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build()
                    .buildResponse();
        }
    }

    @PreAuthorize("hasAnyRole('MESERO', 'ADMINISTRADOR', 'COCINERO')")
    @PutMapping(UPDATE)
    public ResponseEntity<GenericResponse> updateOrder(@RequestBody @Valid OrderUpdateDTO order,
                                                       @RequestParam(required = true) UUID id) {

        System.out.println("Authorities: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());

        try {
            OrderUpdateDTO data = orderService.updateOrder(id, order);
            return GenericResponse
                    .builder()
                    .message("OrderUpdated")
                    .data(data)
                    .status(HttpStatus.OK)
                    .build()
                    .buildResponse();
        } catch (Exception e) {
            return GenericResponse
                    .builder()
                    .message("OrderUpdateFailed: " + e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build()
                    .buildResponse();
        }
    }

    @PreAuthorize("hasAnyRole('MESERO', 'ADMINISTRADOR')")
    @DeleteMapping(DELETE)
    public ResponseEntity<GenericResponse> deleteOrder(@RequestParam(required = true) UUID id) {
        try {
            orderService.deleteOrder(id);
            return GenericResponse
                    .builder()
                    .message("OrderDeleted")
                    .status(HttpStatus.NO_CONTENT)
                    .build()
                    .buildResponse();
        } catch (Exception e) {
            return GenericResponse
                    .builder()
                    .message("OrderDeletionFailed: " + e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build()
                    .buildResponse();
        }
    }
    @PreAuthorize("hasAnyRole('MESERO', 'ADMINISTRADOR','COCINERO')")
    @GetMapping(GET_ALL)
    public ResponseEntity<GenericResponse> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<OrderSummaryDTO> orders = orderService.findAll(PageRequest.of(page, size));
            return GenericResponse.builder()
                    .message("OrdersFetched")
                    .data(orders)
                    .status(HttpStatus.OK)
                    .build()
                    .buildResponse();
        } catch (Exception e) {
            return GenericResponse.builder()
                    .message("OrderFetchFailed: " + e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build()
                    .buildResponse();
        }
    }

    @PreAuthorize("hasAnyRole('MESERO', 'ADMINISTRADOR')")
    @GetMapping("/{_id}")
    public ResponseEntity<GenericResponse> getOrderById(@PathVariable ("_id") String _id){

        UUID id = UUID.fromString(_id);

        Order order = orderService.findById(id);

        return GenericResponse.builder()
                .message("Order retrieved successfully")
                .data(order)
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @PreAuthorize("hasAnyRole('MESERO', 'ADMINISTRADOR', 'COCINERO')")
    @GetMapping(ORDER_STATE + "/{state}")
    public ResponseEntity<GenericResponse> getOrderByState(@PathVariable ("state") String state,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page, limit);

        Page<Order> orders = orderService.findAllByState(state, pageable);

        return GenericResponse.builder()
                .message("Orders retrieved successfully")
                .data(orders)
                .status(HttpStatus.OK)
                .build().buildResponse();
    }


    @PreAuthorize("hasAnyRole('MESERO', 'ADMINISTRADOR')")
    @GetMapping(ORDER_WAITER + "/{id}")
    public ResponseEntity<GenericResponse> getOrdersByWaiterId(@PathVariable("id") Integer waiterId,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "20") int limit) {

        Pageable pageable = PageRequest.of(page, limit);

        Page<Order> order = orderService.findAllByWaiterId(waiterId, pageable);

        return GenericResponse.builder()
                .message("Orders retrieved successfully")
                .data(order)
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @PreAuthorize("hasAnyRole('MESERO', 'ADMINISTRADOR', 'COCINERO')")
    @GetMapping(ORDER_PAYMENT + "/{paymentMethod}")
    public ResponseEntity<GenericResponse> getOrdersByPaymentMethod(@PathVariable("paymentMethod") String paymentMethod,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "20") int limit) {

        Pageable pageable = PageRequest.of(page, limit);

        Page<Order> orders = orderService.findAllByPaymentMethod(paymentMethod, pageable);

        return GenericResponse.builder()
                .message("Orders retrieved successfully")
                .data(orders)
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

}
