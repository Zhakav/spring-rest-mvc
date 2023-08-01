package zhakav.springframework.springrestmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import zhakav.springframework.springrestmvc.model.Beer;
import zhakav.springframework.springrestmvc.model.BeerStyle;
import zhakav.springframework.springrestmvc.model.Customer;
import zhakav.springframework.springrestmvc.service.BeerService;
import zhakav.springframework.springrestmvc.service.CustomerService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    CustomerService customerService;
    @Autowired
    ObjectMapper objectMapper;
    List<Customer> customers;
    @BeforeEach
    void setup(){

        Map<UUID,Customer> customerMap=new HashMap<>();

        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Customer 1")
                .version(1)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Customer 2")
                .version(1)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
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

        customers=new ArrayList<>(customerMap.values());
    }
    @Test
    void getByID() throws Exception {

        Customer customerTest=customers.get(0);

        given(customerService.getById(customerTest.getId())).willReturn(customerTest);

        mockMvc.perform(get(CustomerController.PATH_ID, customerTest.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(customerTest.getId().toString() )))
                .andExpect(jsonPath("$.name", is(customerTest.getName())));

    }
    @Test
    void getAll() throws Exception{

        given(customerService.getAll()).willReturn(customers);

        mockMvc.perform(get(CustomerController.PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(3)));


    }

    @Test
    void createCustomer() throws Exception {

        Customer customer=customers.get(0);
        Customer returnedCustomer=customers.get(1);
        customer.setId(null);

        given(customerService.save(any(Customer.class))).willReturn(returnedCustomer);

        mockMvc.perform(post(CustomerController.PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location",is("/api/v1/customer/"+returnedCustomer.getId())))
                .andExpect(jsonPath("$.id",is(returnedCustomer.getId().toString())));

    }

    @Test
    void updateCustomer() throws Exception{

        Customer customer=customers.get(0);

        given(customerService.updateById(customer,customer.getId())).willReturn(customer);

        mockMvc.perform(put(CustomerController.PATH_ID,customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id",is(customer.getId().toString())));

        verify(customerService).updateById(eq(customer),eq(customer.getId()));
    }
    @Test
    void deleteCustomer() throws Exception{

        Customer customer=customers.get(0);


        given(customerService.deleteById(customer.getId())).willReturn(customer);

        mockMvc.perform(delete(CustomerController.PATH_ID,customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.id",is(customer.getId().toString())));

        verify(customerService).deleteById(eq(customer.getId()));
    }
    @Test
    void patchCustomer() throws Exception{

        Customer customer=customers.get(0);
        customer.setName("New Name");

        Customer requestBody=Customer.builder()
                .name("New Name")
                .build();

        given(customerService.patchById(any(Customer.class),eq(customer.getId()))).willReturn(customer);

        mockMvc.perform(patch(CustomerController.PATH_ID,customer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id",is(customer.getId().toString())))
                .andExpect(jsonPath("$.name",is(requestBody.getName())));

        verify(customerService).patchById(eq(requestBody),eq(customer.getId()));
    }
}