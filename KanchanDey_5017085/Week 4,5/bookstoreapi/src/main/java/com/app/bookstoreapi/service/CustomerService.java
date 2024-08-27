package com.app.bookstoreapi.service;
import java.util.List;
import java.util.Optional;
import com.app.bookstoreapi.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.app.bookstoreapi.repo.CustomerRepository;

import jakarta.persistence.OptimisticLockException;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public void save(Customer customer){
        if(customer.getPassword()!=null && !customer.getPassword().isEmpty())
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        else
            throw new IllegalArgumentException("Password cannot be null");
        customerRepo.save(customer);
    }
    public List<Customer> getAllCustomers(){
        return customerRepo.findAll();
    }
    public ResponseEntity<Object> getByFirstName(String firstName){
        Optional<List<Customer>> existing=customerRepo.findByFirstName(firstName);
        if(!existing.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer with firstname: "+firstName+" not found");
        return ResponseEntity.ok(existing);
    }
    public ResponseEntity<Object> getByLastName(String lastName){
        Optional<List<Customer>> existing=customerRepo.findByLastName(lastName);
        if(!existing.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer with lastname: "+lastName+" not found");
        return ResponseEntity.ok(existing);
    }
    public ResponseEntity<Object> getByAge(Integer age){
        Optional<List<Customer>> existing=customerRepo.findByAge(age);
        if(!existing.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer of age: "+age+" not found");
        return ResponseEntity.ok(existing);
    }
    public ResponseEntity<Object> getByNumber(Long number){
        Optional<List<Customer>> existing=customerRepo.findByNumber(number);
        if(!existing.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer having number: "+number+" not found");
        return ResponseEntity.ok(existing);
    }
    public ResponseEntity<String> saveCustomer(Customer customer){
        try{
            customerRepo.save(customer);
            return ResponseEntity.ok("Customer saved with id: "+customer.getId());
        }
        catch(OptimisticLockException e){
            return new ResponseEntity<>("Failed to save customer: Concurrent Modification detected.",HttpStatus.CONFLICT);
        }
    }
    public ResponseEntity<Object> updateEntireCustomer(Long id, Customer customer){
        try {
            Optional<Customer> existing=customerRepo.findById(id);
            if(!existing.isPresent()){
                customerRepo.save(customer);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not present before, so created. Please save customers with POST method");
            }
            Customer cust=existing.get();
            cust.setFirstName(customer.getFirstName());
            cust.setMiddleName(customer.getMiddleName());
            cust.setLastName(customer.getLastName());
            cust.setAge(customer.getAge());
            cust.setNumber(customer.getNumber());
            customerRepo.save(cust);
            return ResponseEntity.status(HttpStatus.CREATED).body("Customer with id: "+id+" updated");
        } catch (OptimisticLockException e) {
            return new ResponseEntity<>("Failed to save customer: Concurrent Modification detected.",HttpStatus.CONFLICT);
        }
    }
    public ResponseEntity<Object> updateCustomer(Long id,Customer newCustomer){
        Optional<Customer> existing=customerRepo.findById(id);
        if(!existing.isPresent())
            return ResponseEntity.ok("Customer with id: "+id+" not present");
        Customer oldCustomer=existing.get();
        if(newCustomer.getFirstName()!=null)
            oldCustomer.setFirstName(newCustomer.getFirstName());
        if(newCustomer.getMiddleName()!=null)
            oldCustomer.setMiddleName(newCustomer.getMiddleName());
        if(newCustomer.getLastName()!=null)
            oldCustomer.setLastName(newCustomer.getLastName());
        if(newCustomer.getAge()!=null)
            oldCustomer.setAge(newCustomer.getAge());
        if(newCustomer.getNumber()!=null)
            oldCustomer.setNumber(newCustomer.getNumber());
        customerRepo.save(oldCustomer);
        return ResponseEntity.ok("Customer with id: "+id+" updated");
    }
    public ResponseEntity<Object> deleteById(Long id){
        Optional<Customer> existing=customerRepo.findById(id);
        if(!existing.isPresent()){
            return ResponseEntity.ok("Customer with id: "+id+" not present");
        }
        customerRepo.delete(existing.get());
        return ResponseEntity.ok("Customer with id: "+id+" deleted");
    }
    public ResponseEntity<String> deleteAll(){
        customerRepo.deleteAll();
        return ResponseEntity.ok("All Customers deleted");
    }
    public Customer getCustomerById(Long id) {
        Optional<Customer> customer=customerRepo.findById(id);
        return customer.orElseThrow(()->new RuntimeException("Customer not found with id: "+id));
    }
}