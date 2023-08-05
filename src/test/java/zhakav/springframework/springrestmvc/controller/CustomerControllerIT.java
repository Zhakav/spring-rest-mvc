package zhakav.springframework.springrestmvc.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import zhakav.springframework.springrestmvc.entity.Beer;
import zhakav.springframework.springrestmvc.entity.Customer;
import zhakav.springframework.springrestmvc.exception.NotFoundException;
import zhakav.springframework.springrestmvc.mapper.CustomerMapper;
import zhakav.springframework.springrestmvc.model.BeerDTO;
import zhakav.springframework.springrestmvc.model.CustomerDTO;
import zhakav.springframework.springrestmvc.repository.CustomerRepository;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Test
    @Rollback
    @Transactional
    public void delete(){

        Customer customer= customerRepository.findAll().get(0);

        ResponseEntity response= customerController.deleteById(customer.getId());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(((CustomerDTO)response.getBody()).getId()).isEqualTo(customer.getId());
        assertThat(((CustomerDTO)response.getBody()).getName()).isEqualTo(customer.getName());
        assertThat(customerRepository.findById(customer.getId())).isEmpty();



    }

    @Test
    public void deleteNotFound(){

        assertThrows(NotFoundException.class,()->{

            customerController.deleteById(UUID.randomUUID());

        });

    }

    @Test
    public void updateNotFound(){

        assertThrows(NotFoundException.class,()->{

            customerController.updateById(UUID.randomUUID(), CustomerDTO.builder().build());

        });

    }
    @Test
    @Rollback
    @Transactional
    public void update(){

        Customer customer= customerRepository.findAll().get(0);
        CustomerDTO customerDTO= customerMapper.customerToCustomerDTO(customer);

        customerDTO.setId(null);


        final String customerName="Updated Beer";

        customerDTO.setName(customerName);

        ResponseEntity response= customerController.updateById(customer.getId(),customerDTO);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(((CustomerDTO)response.getBody()).getName()).isEqualTo(customerName);

    }

    @Test
    @Rollback
    @Transactional
    public void save(){

        CustomerDTO customerDTO=CustomerDTO.builder()
                .name("Ali")
                .build();

        ResponseEntity response= customerController.save(customerDTO);
        CustomerDTO customerResponse= (CustomerDTO) response.getBody();
        Customer customer= customerRepository.findById(customerResponse.getId()).get();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation()).isNotNull();
        assertThat(customer).isNotNull();

    }
    @Test
    public void getByIdNotFound(){

        assertThrows(NotFoundException.class,()->{

            customerController.getById(UUID.randomUUID());

        });

    }
    @Test
    public void getById(){

        Customer customer= customerRepository.findAll().get(0);

        CustomerDTO customerDTO= customerController.getById(customer.getId());

        assertThat(customerDTO).isNotNull();
        assertThat(customerDTO.getName()).isEqualTo(customer.getName());
        assertThat(customerDTO.getId()).isEqualTo(customer.getId());



    }

    @Test
    public void listCustomer(){

        List<CustomerDTO> listOfCustomer= customerController.getAll();

        assertThat(listOfCustomer.size()).isEqualTo(3);

    }

    @Test
    @Rollback
    @Transactional
    public void emptyListCustomers(){

        customerRepository.deleteAll();
        List<CustomerDTO> listOfCustomers= customerController.getAll();

        assertThat(listOfCustomers.size()).isEqualTo(0);

    }

}