package zhakav.springframework.springrestmvc.repository;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.Assert;
import zhakav.springframework.springrestmvc.entity.Customer;

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

}