package zhakav.springframework.springrestmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import zhakav.springframework.springrestmvc.model.BeerDTO;
import zhakav.springframework.springrestmvc.model.BeerStyle;
import zhakav.springframework.springrestmvc.service.BeerService;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    BeerService beerService;
    @Autowired
    ObjectMapper objectMapper;
    List<BeerDTO> beers;
    @BeforeEach
    void setup(){

        Map<UUID, BeerDTO>beerMap=new HashMap<>();


        BeerDTO beer1 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beer2 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beer3 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(),beer1);
        beerMap.put(beer2.getId(),beer2);
        beerMap.put(beer3.getId(),beer3);

        beers=new ArrayList<>(beerMap.values());
    }

    @Test
    void getByIdNotFound() throws Exception {

        given(beerService.getByID(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(BeerController.PATH_ID,UUID.randomUUID()))
                .andExpect(status().isNotFound());

    }
    @Test
    void getByID() throws Exception {

        BeerDTO beerTest=beers.get(0);

        given(beerService.getByID(beerTest.getId())).willReturn(Optional.of(beerTest));

        mockMvc.perform(get(BeerController.PATH_ID, beerTest.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(beerTest.getId().toString() )))
                .andExpect(jsonPath("$.beerName", is(beerTest.getBeerName())));

    }
    @Test
    void getAll() throws Exception{

        given(beerService.getAll(null)).willReturn(beers);

        mockMvc.perform(get(BeerController.PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(3)));


    }

    @Test
    void createNewBeer() throws Exception {

        BeerDTO newBeer=beers.get(0);

        newBeer.setId(null);
        newBeer.setVersion(null);

        given(beerService.save(any(BeerDTO.class))).willReturn(beers.get(1));

        mockMvc.perform(post(BeerController.PATH)
                .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newBeer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location",is("/api/v1/beer/"+beers.get(1).getId())))
                .andExpect(jsonPath("$.id",is(beers.get(1).getId().toString())));

    }

    @Test
    void createNewBeerNullName() throws Exception {

        BeerDTO newBeer=BeerDTO.builder()
                .upc("Some UPC")
                .price(BigDecimal.valueOf(20))
                .beerStyle(BeerStyle.GOSE)
                .quantityOnHand(0)
                .build();

        given(beerService.save(any(BeerDTO.class))).willReturn(beers.get(0));

        MvcResult mvcResult=mockMvc.perform(post(BeerController.PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBeer)))
                .andExpect(jsonPath("$.length()",is(2)))
                .andExpect(status().isBadRequest()).andReturn();

        log.debug("VALIDATION EXCEPTION : ");
        log.debug(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void updateBeer() throws Exception{

        BeerDTO beer=beers.get(0);

        given(beerService.updateById(beer,beer.getId())).willReturn(Optional.of(beer));

        mockMvc.perform(put(BeerController.PATH_ID,beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id",is(beer.getId().toString())));

        verify(beerService).updateById(eq(beer),eq(beer.getId()));
    }
    @Test
    void deleteBeer() throws Exception{

        BeerDTO beer=beers.get(0);

        given(beerService.deleteById(beer.getId())).willReturn(Optional.of(beer));

        mockMvc.perform(delete(BeerController.PATH_ID,beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.id",is(beer.getId().toString())));

        verify(beerService).deleteById(eq(beer.getId()));
    }

    @Test
    void patchBeer() throws Exception{

        BeerDTO beer = beers.get(0);
        beer.setBeerName("New Name");

        BeerDTO requestBody= BeerDTO.builder()
                .beerName("New Name")
                .build();

        given(beerService.patchById(any(BeerDTO.class),eq(beer.getId()))).willReturn(Optional.of(beer));

        mockMvc.perform(patch(BeerController.PATH_ID,beer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id",is(beer.getId().toString())))
                .andExpect(jsonPath("$.beerName",is(requestBody.getBeerName())));

        verify(beerService).patchById(eq(requestBody),eq(beer.getId()));
    }
}