package zhakav.springframework.springrestmvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import zhakav.springframework.springrestmvc.exception.NotFoundException;
import zhakav.springframework.springrestmvc.mapper.CustomerMapper;
import zhakav.springframework.springrestmvc.model.BeerDTO;
import zhakav.springframework.springrestmvc.model.CustomerDTO;
import zhakav.springframework.springrestmvc.repository.CustomerRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    @Override
    public Optional<CustomerDTO> getById(UUID id) {

        return Optional.ofNullable(
                customerMapper.customerToCustomerDTO(
                        customerRepository.findById(id)
                                .orElse(null)));

    }

    @Override
    public List<CustomerDTO> getAll() {

        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO save(CustomerDTO customer) {
        return customerMapper.customerToCustomerDTO(
                customerRepository.save(customerMapper.customerDTOTOCustomer(customer))) ;
    }

    @Override
    public Optional<CustomerDTO> updateById(CustomerDTO customer, UUID id) {
        AtomicReference<Optional<CustomerDTO>> atomicReference= new AtomicReference<>();

        customerRepository.findById(id).ifPresentOrElse(foundCustomer->{

            foundCustomer.setName(customer.getName());
            foundCustomer.setUpdateDate(LocalDateTime.now());

            atomicReference.set(Optional.of(customerMapper.customerToCustomerDTO(customerRepository.save(foundCustomer))));
        },()->{
            throw new NotFoundException();
            //atomicReference.set(Optional.empty());
        });

        return Optional.ofNullable(customerMapper.customerToCustomerDTO(customerRepository.findById(id).get()));

    }

    @Override
    public Optional<CustomerDTO> patchById(CustomerDTO customer, UUID id) {

        AtomicReference<Optional<CustomerDTO>> atomicReference= new AtomicReference<>();

        customerRepository.findById(id).ifPresentOrElse(customerFound->{

            if(customer.getName()!=null)
                customerFound.setName(customer.getName());

            atomicReference.set(Optional.of(
                    customerMapper.customerToCustomerDTO(
                            customerRepository.save(customerFound))));

        },()->{

            throw new NotFoundException();

        });

        return Optional.ofNullable(
                customerMapper.customerToCustomerDTO(
                        customerRepository.findById(id).get()));
    }

    @Override
    public Optional<CustomerDTO> deleteById(UUID id) {

        Optional<CustomerDTO> customerDTO= Optional.ofNullable(customerMapper.customerToCustomerDTO(customerRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException();
        })));

        customerRepository.deleteById(id);

        return customerDTO;

    }
}
