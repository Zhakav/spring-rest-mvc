package zhakav.springframework.springrestmvc.repository;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.Assert;
import zhakav.springframework.springrestmvc.entity.Beer;
import zhakav.springframework.springrestmvc.entity.Customer;
import zhakav.springframework.springrestmvc.model.BeerStyle;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void saveCustomerTest(){

        Customer customer= customerRepository.save(Customer.builder()
                .name("Abdollah")
                .build());

        assertThat(customer).isNotNull();
        assertThat(customer.getId()).isNotNull();
    }
    @Test
    public void saveNullNameCustomerTest(){
        assertThrows(ConstraintViolationException.class,()->{

            Customer customer= customerRepository.save(Customer.builder()
                    .build());

            customerRepository.flush();

        });

    }

    @Test
    public void saveToLongCustomerTest(){
        assertThrows(ConstraintViolationException.class,()->{

            Customer customer= customerRepository.save(Customer.builder()
                    .name("Customer 00000000000000000000000000000000000000000000000000000000000000000000000000000")
                    .build());

            customerRepository.flush();

        });

    }
}