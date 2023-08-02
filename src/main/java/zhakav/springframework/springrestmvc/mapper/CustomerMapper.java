package zhakav.springframework.springrestmvc.mapper;

import org.mapstruct.Mapper;
import zhakav.springframework.springrestmvc.entity.Customer;
import zhakav.springframework.springrestmvc.model.CustomerDTO;

@Mapper
public interface CustomerMapper {

    Customer customerDTOTOCustomer(CustomerDTO customerDTO);
    CustomerDTO customerToCustomerDTO(Customer customer);

}
