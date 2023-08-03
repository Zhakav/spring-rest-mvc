package zhakav.springframework.springrestmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zhakav.springframework.springrestmvc.entity.Customer;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
