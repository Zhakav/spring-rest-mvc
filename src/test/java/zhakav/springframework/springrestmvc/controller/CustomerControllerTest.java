package zhakav.springframework.springrestmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import zhakav.springframework.springrestmvc.model.CustomerDTO;
import zhakav.springframework.springrestmvc.service.CustomerService;

import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.core.Is.is;
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
    List<CustomerDTO> customers;
    @BeforeEach
    void setup(){

        Map<UUID, CustomerDTO> customerMap=new HashMap<>();

        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Customer 1")
                .version(1)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Customer 2")
                .version(1)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDTO customer3 = CustomerDTO.builder()
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

        CustomerDTO customerTest=customers.get(0);

        given(customerService.getById(customerTest.getId())).willReturn(Optional.of(customerTest));

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

        CustomerDTO customer=customers.get(0);
        CustomerDTO returnedCustomer=customers.get(1);
        customer.setId(null);

        given(customerService.save(any(CustomerDTO.class))).willReturn(returnedCustomer);

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

        CustomerDTO customer=customers.get(0);

        given(customerService.updateById(customer,customer.getId())).willReturn(Optional.of(customer));

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

        CustomerDTO customer=customers.get(0);


        given(customerService.deleteById(customer.getId())).willReturn(Optional.of(customer));

        mockMvc.perform(delete(CustomerController.PATH_ID,customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.id",is(customer.getId().toString())));

        verify(customerService).deleteById(eq(customer.getId()));
    }
    @Test
    void getByIdNotFound() throws Exception {

        given(customerService.getById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(CustomerController.PATH_ID,UUID.randomUUID()))
                .andExpect(status().isNotFound());

    }
    @Test
    void patchCustomer() throws Exception{

        CustomerDTO customer=customers.get(0);
        customer.setName("New Name");

        CustomerDTO requestBody= CustomerDTO.builder()
                .name("New Name")
                .build();

        given(customerService.patchById(any(CustomerDTO.class),eq(customer.getId()))).willReturn(Optional.of(customer));

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