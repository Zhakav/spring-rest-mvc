package zhakav.springframework.springrestmvc.controller;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import zhakav.springframework.springrestmvc.entity.Beer;
import zhakav.springframework.springrestmvc.exception.NotFoundException;
import zhakav.springframework.springrestmvc.mapper.BeerMapper;
import zhakav.springframework.springrestmvc.model.BeerDTO;
import zhakav.springframework.springrestmvc.repository.BeerRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;
    @Test
    @Rollback
    @Transactional
    public void update(){

        Beer beer= beerRepository.findAll().get(0);
        BeerDTO beerDTO=beerMapper.beerToBeerDTO(beer);

        beerDTO.setId(null);
        beerDTO.setVersion(null);

        final String beerName="Updated Beer";

        beerDTO.setBeerName(beerName);

        ResponseEntity response=beerController.updateById(beer.getId(),beerDTO);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(((BeerDTO)response.getBody()).getBeerName()).isEqualTo(beerName);

    }

    @Test
    @Rollback
    @Transactional
    public void save(){

        BeerDTO beerDTO=BeerDTO.builder()
                .beerName("Silver Wolf")
                .build();

        ResponseEntity response=beerController.save(beerDTO);
        BeerDTO beerResponse= (BeerDTO) response.getBody();
        Beer beer=beerRepository.findById(beerResponse.getId()).get();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation()).isNotNull();
        assertThat(beer).isNotNull();

    }
    @Test
    public void getByIdNotFound(){

        assertThrows(NotFoundException.class,()->{

            beerController.getByID(UUID.randomUUID());

        });

    }
    @Test
    public void getById(){

        Beer beer=beerRepository.findAll().get(0);

        BeerDTO beerDTO=beerController.getByID(beer.getId());

        assertThat(beerDTO).isNotNull();
        assertThat(beerDTO.getBeerName()).isEqualTo(beer.getBeerName());
        assertThat(beerDTO.getId()).isEqualTo(beer.getId());
        assertThat(beerDTO.getPrice()).isEqualTo(beer.getPrice());


    }

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