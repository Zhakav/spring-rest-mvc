package zhakav.springframework.springrestmvc.controller;

import static org.apache.tomcat.InstanceManagerBindings.get;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import zhakav.springframework.springrestmvc.entity.Beer;
import zhakav.springframework.springrestmvc.exception.NotFoundException;
import zhakav.springframework.springrestmvc.mapper.BeerMapper;
import zhakav.springframework.springrestmvc.model.BeerDTO;
import zhakav.springframework.springrestmvc.repository.BeerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BeerMapper beerMapper;

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

        Beer beer= beerRepository.findAll().get(0);

        ResponseEntity response=beerController.deleteById(beer.getId());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(((BeerDTO)response.getBody()).getId()).isEqualTo(beer.getId());
        assertThat(((BeerDTO)response.getBody()).getBeerName()).isEqualTo(beer.getBeerName());
        assertThat(beerRepository.findById(beer.getId())).isEmpty();

    }

    void getAllBeersByName() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(BeerController.PATH)
                .queryParam("beerName","IPA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()",is(100)));

    }

    @Test
    void patchBeerInvalidName() throws Exception{

        BeerDTO beer = beerMapper.beerToBeerDTO(beerRepository.findAll().get(0));

        BeerDTO requestBody= BeerDTO.builder()
                .beerName("New Name555555555555555" +
                        "5555555555555555555555555555555555" +
                        "5555555555555555555555555555555555555" +
                        "555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555")
                .build();

        MvcResult result=mockMvc.perform(patch(BeerController.PATH_ID,beer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(jsonPath("$.length()",is(1)))
                .andExpect(status().isBadRequest()).andReturn();

        log.debug("ERROR BODY :");
        log.debug(result.getResponse().getContentAsString());
    }

    @Test
    public void deleteNotFound(){

        assertThrows(NotFoundException.class,()->{

            beerController.deleteById(UUID.randomUUID());

        });

    }

    @Test
    public void updateNotFound(){

        assertThrows(NotFoundException.class,()->{

            beerController.updateById(UUID.randomUUID(),BeerDTO.builder().build());

        });

    }
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

        assertThat(listOfBeers.size()).isEqualTo(2413);

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