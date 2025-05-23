package com.ricardo.customerconnect.controller;

import com.ricardo.customerconnect.controller.dto.ApiResponse;
import com.ricardo.customerconnect.controller.dto.CreateCustomerDto;
import com.ricardo.customerconnect.controller.dto.PaginationResponse;
import com.ricardo.customerconnect.controller.dto.UpdateCustomerDto;
import com.ricardo.customerconnect.entity.CustomerEntity;
import com.ricardo.customerconnect.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> createCustomer(@RequestBody CreateCustomerDto dto) {
        customerService.createCustomer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CustomerEntity>> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                               @RequestParam(name = "orderBy", defaultValue = "desc") String orderBy,
                                                               @RequestParam(name = "cpf", required = false) String cpf,
                                                               @RequestParam(name = "email", required = false) String email) {
        var pageResponse = customerService.findAll(page, pageSize, orderBy, cpf, email);

        return ResponseEntity.ok(new ApiResponse<>(pageResponse.getContent(),
                new PaginationResponse(pageResponse.getNumber(),
                        pageResponse.getSize(),
                        pageResponse.getTotalElements(),
                        pageResponse.getTotalPages())));
    }

    @GetMapping(path = "/{custumerId}")
    public ResponseEntity<Optional<CustomerEntity>> findById(@PathVariable("custumerId") Long custumerId) {
        var customer = customerService.findById(custumerId);

        return customer.map(customerEntity -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(customer))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping(path = "/{custumerId}")
    public ResponseEntity<Optional<CustomerEntity>> updateById(@PathVariable("custumerId") Long custumerId,
                                                               @RequestBody UpdateCustomerDto dto) {
        var customer = customerService.updateById(custumerId, dto);

        return customer.isPresent()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/{custumerId}")
    public ResponseEntity<Void> deleteById(@PathVariable("custumerId") Long custumerId) {
        var deleted = customerService.deleteById(custumerId);

        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
