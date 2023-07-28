package zhakav.springframework.springrestmvc.service;

import zhakav.springframework.springrestmvc.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

     Customer getById(UUID id);
     List<Customer> getAll();
     Customer save(Customer customer);

     Customer updateById(Customer customer, UUID id);
}
