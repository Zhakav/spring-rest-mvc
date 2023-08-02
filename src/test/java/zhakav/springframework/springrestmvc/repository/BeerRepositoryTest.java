package zhakav.springframework.springrestmvc.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import zhakav.springframework.springrestmvc.entity.Beer;
import zhakav.springframework.springrestmvc.entity.Customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    public void saveCustomerTest(){

        Beer beer= beerRepository.save(Beer.builder()
                .beerName("Golden Lion")
                .build());

        assertThat(beer).isNotNull();
        assertThat(beer.getId()).isNotNull();
    }

}