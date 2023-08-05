package zhakav.springframework.springrestmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
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
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc= MockMvcBuilders.webAppContextSetup(wac).build();
    }

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
    void patchBeerInvalidName() throws Exception{

        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customerRepository.findAll().get(1));

        CustomerDTO requestBody= CustomerDTO.builder()
                .name("New Name555555555555555" +
                        "5555555555555555555555555555555555" +
                        "5555555555555555555555555555555555555" +
                        "555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555")
                .build();

        MvcResult result=mockMvc.perform(patch(CustomerController.PATH_ID,customerDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(jsonPath("$.length()",is(1)))
                .andExpect(status().isBadRequest()).andReturn();

        log.debug("ERROR BODY :");
        log.debug(result.getResponse().getContentAsString());
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