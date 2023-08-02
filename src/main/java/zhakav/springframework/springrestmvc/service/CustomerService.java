package zhakav.springframework.springrestmvc.service;

import zhakav.springframework.springrestmvc.model.Beer;
import zhakav.springframework.springrestmvc.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    Optional<Customer> getById(UUID id);
     List<Customer> getAll();
     Customer save(Customer customer);
     Optional<Customer> updateById(Customer customer, UUID id);
     Optional<Customer> patchById(Customer customer, UUID id);
     Optional<Customer> deleteById(UUID id);
}
