package zhakav.springframework.springrestmvc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zhakav.springframework.springrestmvc.model.CustomerDTO;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    Map<UUID, CustomerDTO> customerMap;
    CustomerServiceImpl(){

        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Customer 1")
                .version(1)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Customer 2")
                .version(1)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDTO customer3 = CustomerDTO.builder()
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
    public Optional<CustomerDTO> getById(UUID id) {


        log.debug("GET CUSTOMER BY ID -IN CUSTOMER SERVICE -ID : " + id);


        return Optional.of(customerMap.get(id));
    }

    @Override
    public List<CustomerDTO> getAll() {

        log.debug("GET ALL CUSTOMER -IN CUSTOMER SERVICE ");


        return new ArrayList<>(customerMap.values());
    }

    @Override
    public CustomerDTO save(CustomerDTO customer) {

        CustomerDTO savedCustomer= CustomerDTO.builder()
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
    @Override
    public Optional<CustomerDTO> updateById(CustomerDTO customer, UUID id) {

        CustomerDTO exist = customerMap.get(id);

        //if(customer.getName()!=null)
            exist.setName(customer.getName());

        customerMap.put(id,exist);

        return Optional.of(exist);
    }
    @Override
    public Optional<CustomerDTO> patchById(CustomerDTO customer, UUID id) {

        CustomerDTO exist = customerMap.get(id);

        if(customer.getName()!=null)
            exist.setName(customer.getName());

        customerMap.put(id,exist);

        return Optional.of(exist);
    }
    @Override
    public Optional<CustomerDTO> deleteById(UUID id) {

        CustomerDTO deletedCustomer = customerMap.remove(id);

        return Optional.of(deletedCustomer);
    }
}
