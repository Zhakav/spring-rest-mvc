package zhakav.springframework.springrestmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import zhakav.springframework.springrestmvc.model.BeerDTO;
import zhakav.springframework.springrestmvc.repository.BeerRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Test
    public void listBeers(){

        List<BeerDTO> listOfBeers=beerController.getAll();

        assertThat(listOfBeers.size()).isEqualTo(3);

    }

    @Test
    @Rollback
    @Transactional
    public void emptyListBeers(){

        beerRepository.deleteAll();
        List<BeerDTO> listOfBeers=beerController.getAll();

        assertThat(listOfBeers.size()).isEqualTo(0);

    }

}