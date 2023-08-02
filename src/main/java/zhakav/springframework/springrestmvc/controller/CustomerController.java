package zhakav.springframework.springrestmvc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zhakav.springframework.springrestmvc.exception.NotFoundException;
import zhakav.springframework.springrestmvc.model.CustomerDTO;
import zhakav.springframework.springrestmvc.service.CustomerService;

import java.util.List;
import java.util.UUID;
@Slf4j
@RestController
@AllArgsConstructor
public class CustomerController {

    public static final String PATH= "/api/v1/customer";
    public static final String PATH_ID= PATH +"/{customerId}" ;
    private CustomerService customerService;
    @GetMapping(PATH)
    public List<CustomerDTO> getAll(){

        log.debug("GET ALL CUSTOMER -IN CUSTOMER CONTROLLER ");

        return customerService.getAll();

    }
    @GetMapping(PATH_ID)
    public CustomerDTO getById(@PathVariable("customerId") UUID customerId){

        log.debug("GET CUSTOMER BY ID -IN CUSTOMER CONTROLLER -ID : " + customerId);

        return customerService.getById(customerId).orElseThrow(NotFoundException::new);

    }

    @PostMapping(PATH)
    public ResponseEntity<CustomerDTO> save(@RequestBody CustomerDTO customer){

        CustomerDTO savedCustomer=customerService.save(customer);

        log.debug("SAVE CUSTOMER -IN CUSTOMER CONTROLLER -ID : " + savedCustomer.getId());

        HttpHeaders headers=new HttpHeaders();
        headers.add("Location","/api/v1/customer/" + savedCustomer.getId());

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(savedCustomer);

    }

    @PutMapping(PATH_ID)
    private ResponseEntity<CustomerDTO> updateById(
            @PathVariable("customerId") UUID customerId, @RequestBody CustomerDTO customer){

        return new ResponseEntity<>(customerService.updateById(customer,customerId).orElseThrow(NotFoundException::new),HttpStatus.ACCEPTED);

    }
    @PatchMapping(PATH_ID)
    private ResponseEntity<CustomerDTO> patchById(
            @PathVariable("customerId") UUID customerId, @RequestBody CustomerDTO customer){

        return new ResponseEntity<>(customerService.patchById(customer,customerId).orElseThrow(NotFoundException::new),HttpStatus.ACCEPTED);

    }
    @DeleteMapping(PATH_ID)
    public ResponseEntity<CustomerDTO> deleteById(@PathVariable("customerId") UUID customerId){

        return new ResponseEntity<>(customerService.deleteById(customerId).orElseThrow(NotFoundException::new),HttpStatus.NO_CONTENT);


    }
}
