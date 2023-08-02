package zhakav.springframework.springrestmvc.service;

import zhakav.springframework.springrestmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    Optional<CustomerDTO> getById(UUID id);
     List<CustomerDTO> getAll();
     CustomerDTO save(CustomerDTO customer);
     Optional<CustomerDTO> updateById(CustomerDTO customer, UUID id);
     Optional<CustomerDTO> patchById(CustomerDTO customer, UUID id);
     Optional<CustomerDTO> deleteById(UUID id);
}
