package zhakav.springframework.springrestmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zhakav.springframework.springrestmvc.entity.Beer;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
