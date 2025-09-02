package ait.cohort63.shop.controller;

import ait.cohort63.shop.model.dto.CustomerDTO;
import ait.cohort63.shop.model.dto.ProductDTO;
import ait.cohort63.shop.model.entity.Customer;
import ait.cohort63.shop.service.interfaces.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customers")// Указывает, что контроллер обрабатывает запросы, связанные с ресурсом customers
@Tag(name = "Customer controller", description = "Controller for operations with customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {

        this.customerService = customerService;
    }

    @Operation(summary = "Create customer", description = "Add new customer", tags = {"customer"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CustomerDTO.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomerDTO.class))})})

    @PostMapping
    public CustomerDTO saveCustomer(@Parameter(description = "Created customer object") @RequestBody CustomerDTO customerDTO) {
        return customerService.saveCustomer(customerDTO);
    }

    @Operation(summary = "Get customer by id", tags = {"customer"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content =
                    {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDTO.class)),
                            @Content(mediaType = "application/xml",
                                    schema = @Schema(implementation = CustomerDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)})

    @GetMapping("/{id}")
    public CustomerDTO getById(@Parameter(description = "The id needs to be fetched", required = true)@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {

        return customerService.getAllActiveCustomers();
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(id, customerDTO);
    }

    @DeleteMapping("/{customerId}")
    public CustomerDTO remove(@PathVariable("customerId") Long id) {

        return customerService.deleteCustomerById(id);
    }

    @DeleteMapping("/by-name")
    public CustomerDTO removeByName(@RequestParam String name) {

        return customerService.deleteCustomerByName(name);
    }

    @PutMapping("/restore/{id}")
    public CustomerDTO restoreById(@PathVariable Long id) {

        return customerService.restoreCustomerById(id);
    }

    @GetMapping("/count")
    public long getCustomerCount() {

        return customerService.getCustomerCount();
    }
}
