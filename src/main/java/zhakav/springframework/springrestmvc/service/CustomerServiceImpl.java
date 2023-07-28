package zhakav.springframework.springrestmvc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zhakav.springframework.springrestmvc.model.Customer;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    Map<UUID, Customer> customerMap;
    CustomerServiceImpl(){

        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Customer 1")
                .version(1)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Customer 2")
                .version(1)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Customer 3")
                .version(1)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        customerMap = new HashMap<>();
        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);

    }
    @Override
    public Customer getById(UUID id) {


        log.debug("GET CUSTOMER BY ID -IN CUSTOMER SERVICE -ID : " + id);


        return customerMap.get(id);
    }

    @Override
    public List<Customer> getAll() {

        log.debug("GET ALL CUSTOMER -IN CUSTOMER SERVICE ");


        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer save(Customer customer) {

        Customer savedCustomer=Customer.builder()
                .id(UUID.randomUUID())
                .name(customer.getName())
                .version(1)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        customerMap.put(savedCustomer.getId(),savedCustomer);

        log.debug("SAVE CUSTOMER -IN CUSTOMER CONTROLLER -ID : " + savedCustomer.getId());
        

        return savedCustomer;
    }
}
