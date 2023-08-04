package zhakav.springframework.springrestmvc.repository;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import zhakav.springframework.springrestmvc.entity.Beer;
import zhakav.springframework.springrestmvc.entity.Customer;
import zhakav.springframework.springrestmvc.model.BeerStyle;

import java.math.BigDecimal;

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
                .beerStyle(BeerStyle.PALE_ALE)
                        .price(BigDecimal.valueOf(12.9))
                        .upc("654454554554")
                        .quantityOnHand(50)
                .build());

        beerRepository.flush();

        assertThat(beer).isNotNull();
        assertThat(beer.getId()).isNotNull();
    }

    @Test
    public void saveInvalidCustomerTest(){
        assertThrows(ConstraintViolationException.class,()->{

            Beer beer= beerRepository.save(Beer.builder()
                    .beerName("Golden Lion")
                    .upc("654454554554")
                    .quantityOnHand(50)
                    .build());

            beerRepository.flush();

        });

    }

    @Test
    public void saveToLongCustomerTest(){
        assertThrows(ConstraintViolationException.class,()->{

            Beer beer= beerRepository.save(Beer.builder()
                    .beerName("Golden Lion 00000000000000000000000000000000000000000000000000000000000000000000000000000")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("654454554554")
                    .quantityOnHand(50)
                    .build());

            beerRepository.flush();

        });

    }

}