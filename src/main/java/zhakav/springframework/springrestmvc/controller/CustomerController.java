package zhakav.springframework.springrestmvc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zhakav.springframework.springrestmvc.model.Beer;
import zhakav.springframework.springrestmvc.model.Customer;
import zhakav.springframework.springrestmvc.service.BeerService;
import zhakav.springframework.springrestmvc.service.CustomerService;

import java.util.List;
import java.util.UUID;
@Slf4j
@RestController
@AllArgsConstructor
public class CustomerController {

    public static final String PATH= "/api/v1/customer";
    public static final String PATH_ID= PATH +"/${customerId}" ;
    private CustomerService customerService;
    @GetMapping(PATH)
    public List<Customer> getAll(){

        log.debug("GET ALL CUSTOMER -IN CUSTOMER CONTROLLER ");

        return customerService.getAll();

    }
    @GetMapping(PATH_ID)
    public Customer getById(@PathVariable("customerId") UUID customerId){

        log.debug("GET CUSTOMER BY ID -IN CUSTOMER CONTROLLER -ID : " + customerId);

        return customerService.getById(customerId);

    }

    @PostMapping(PATH)
    public ResponseEntity<Customer> save(@RequestBody Customer customer){

        Customer savedCustomer=customerService.save(customer);

        log.debug("SAVE CUSTOMER -IN CUSTOMER CONTROLLER -ID : " + savedCustomer.getId());

        HttpHeaders headers=new HttpHeaders();
        headers.add("Location","/api/v1/customer/" + savedCustomer.getId());

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(savedCustomer);

    }

    @PutMapping(PATH_ID)
    private ResponseEntity<Customer> updateById(
            @PathVariable("customerId") UUID customerId, @RequestBody Customer customer){

        return new ResponseEntity<>(customerService.updateById(customer,customerId),HttpStatus.ACCEPTED);

    }
    @PatchMapping(PATH_ID)
    private ResponseEntity<Customer> patchById(
            @PathVariable("customerId") UUID customerId, @RequestBody Customer customer){

        return new ResponseEntity<>(customerService.patchById(customer,customerId),HttpStatus.ACCEPTED);

    }
    @DeleteMapping(PATH_ID)
    public ResponseEntity<Customer> deleteById(@PathVariable("customerId") UUID customerId){

        return new ResponseEntity<>(customerService.deleteById(customerId),HttpStatus.NO_CONTENT);


    }
}
