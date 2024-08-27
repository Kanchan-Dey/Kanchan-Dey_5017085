package com.app.bookstoreapi.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.app.bookstoreapi.entity.Customer;
import com.app.bookstoreapi.service.CustomMetricsService;
import com.app.bookstoreapi.service.CustomerService;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer")
@Validated
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomMetricsService customMetricsService;
    @GetMapping(produces = {"application/json","application/xml"})
    public List<Customer> getAllCustomers(){
        customMetricsService.incrementCounter();
        return customerService.getAllCustomers();
    }
    @GetMapping("/{id}")
    public EntityModel<Customer> getCustomerById(@PathVariable Long id){
        Customer customer=customerService.getCustomerById(id);
        EntityModel<Customer> resource=EntityModel.of(customer);
        resource.add(linkTo(methodOn(CustomerController.class).getCustomerById(id)).withSelfRel());
        resource.add(linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("all-customers"));
        return resource;    
    }
    @PostMapping(consumes = {"application/json","application/xml"},
                produces={"application/json","application/xml"})
    public ResponseEntity<String> insertCustomer(@Valid @RequestBody Customer customer){
        return customerService.saveCustomer(customer);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEntireCustomer(@PathVariable Long id, @RequestBody Customer customer){
        return customerService.updateEntireCustomer(id,customer);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable Long id, @RequestBody Customer customer){
        return customerService.updateCustomer(id,customer);
    }
    @DeleteMapping
    public ResponseEntity<String> deleteAll(){
        customerService.deleteAll();
        return ResponseEntity.ok("All Customers deleted successfully");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(Long id){
        return customerService.deleteById(id);
    }
}
