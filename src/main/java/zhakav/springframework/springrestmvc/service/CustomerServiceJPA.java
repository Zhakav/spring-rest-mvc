package zhakav.springframework.springrestmvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import zhakav.springframework.springrestmvc.mapper.CustomerMapper;
import zhakav.springframework.springrestmvc.model.CustomerDTO;
import zhakav.springframework.springrestmvc.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    @Override
    public Optional<CustomerDTO> getById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<CustomerDTO> getAll() {
        return null;
    }

    @Override
    public CustomerDTO save(CustomerDTO customer) {
        return null;
    }

    @Override
    public Optional<CustomerDTO> updateById(CustomerDTO customer, UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<CustomerDTO> patchById(CustomerDTO customer, UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<CustomerDTO> deleteById(UUID id) {
        return Optional.empty();
    }
}
