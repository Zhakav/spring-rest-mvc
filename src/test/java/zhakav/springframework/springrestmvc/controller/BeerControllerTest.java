package zhakav.springframework.springrestmvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import zhakav.springframework.springrestmvc.model.Beer;
import zhakav.springframework.springrestmvc.model.BeerStyle;
import zhakav.springframework.springrestmvc.service.BeerService;
import zhakav.springframework.springrestmvc.service.BeerServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    BeerService beerService;
    @Autowired
    ObjectMapper objectMapper;
    List<Beer> beers;
    @BeforeEach
    void setup(){

        Map<UUID,Beer>beerMap=new HashMap<>();


        Beer beer1 = Beer.builder()
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

        Beer beer2 = Beer.builder()
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

        Beer beer3 = Beer.builder()
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
    void getByID() throws Exception {

        Beer beerTest=beers.get(0);

        given(beerService.getByID(beerTest.getId())).willReturn(beerTest);

        mockMvc.perform(get(BeerController.PATH + beerTest.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(beerTest.getId().toString() )))
                .andExpect(jsonPath("$.beerName", is(beerTest.getBeerName())));

    }
    @Test
    void getAll() throws Exception{

        given(beerService.getAll()).willReturn(beers);

        mockMvc.perform(get(BeerController.PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(3)));


    }

    @Test
    void createNewBeer() throws Exception {

        Beer newBeer=beers.get(0);

        newBeer.setId(null);
        newBeer.setVersion(null);

        given(beerService.save(any(Beer.class))).willReturn(beers.get(1));

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
    void updateBeer() throws Exception{

        Beer beer=beers.get(0);

        given(beerService.updateById(beer,beer.getId())).willReturn(beer);

        mockMvc.perform(put(BeerController.PATH+beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id",is(beer.getId().toString())));

        verify(beerService).updateById(eq(beer),eq(beer.getId()));
    }
    @Test
    void deleteBeer() throws Exception{

        Beer beer=beers.get(0);

        given(beerService.deleteById(beer.getId())).willReturn(beer);

        mockMvc.perform(delete(BeerController.PATH+beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.id",is(beer.getId().toString())));

        verify(beerService).deleteById(eq(beer.getId()));
    }

//    @Test
//    void patchBeer() throws Exception{
//
//        Beer beer = beers.get(0);
//
//        Map<String, Object> beerMap = new HashMap<>();
//        beerMap.put("beerName", "New Name");
//
//        mockMvc.perform(patch("/api/v1/beer/" + beer.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(beerMap)))
//                .andExpect(status().isAccepted());
//        verify(beerService).patchById(eq(beer),eq(beer.getId()));
//    }
}