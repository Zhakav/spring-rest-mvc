package zhakav.springframework.springrestmvc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import zhakav.springframework.springrestmvc.model.BeerDTO;
import zhakav.springframework.springrestmvc.model.BeerStyle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID, BeerDTO> beerMap;
    public BeerServiceImpl(){

        beerMap=new HashMap<>();


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


    }
    @Override
    public List<BeerDTO> getAll(String beerName, BeerStyle beerStyle, boolean showInventory){

        log.debug("GET ALL BEERS -IN BEER SERVICE ");

        return new ArrayList<>(beerMap.values());

    }
    @Override
    public Optional<BeerDTO> getByID(UUID id) {

        log.debug("GET BEER BY ID -IN BEER SERVICE -ID : " + id);

        BeerDTO beer=beerMap.get(id);

        return Optional.of(beer);
    }

    @Override
    public BeerDTO save(BeerDTO beer) {

        BeerDTO savedBeer= BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .quantityOnHand(beer.getQuantityOnHand())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(savedBeer.getId(),savedBeer);

        return savedBeer;
    }

    @Override
    public Optional<BeerDTO> updateById(BeerDTO beer, UUID id) {

        BeerDTO exist = beerMap.get(id);

//        if(beer.getBeerName()!=null)
            exist.setBeerName(beer.getBeerName());
//        if(beer.getBeerStyle()!=null)
            exist.setBeerStyle(beer.getBeerStyle());
//        if(beer.getPrice()!=null)
            exist.setPrice(beer.getPrice());
//        if(beer.getUpc()!=null)
            exist.setUpc(beer.getUpc());
//        if(beer.getQuantityOnHand()!=null)
            exist.setQuantityOnHand(beer.getQuantityOnHand());


        beerMap.put(id,exist);

        return Optional.of(exist);
    }
    @Override
    public Optional<BeerDTO> patchById(BeerDTO beer, UUID id) {

        BeerDTO exist = beerMap.get(id);

        if(beer.getBeerName()!=null)
            exist.setBeerName(beer.getBeerName());
        if(beer.getBeerStyle()!=null)
            exist.setBeerStyle(beer.getBeerStyle());
        if(beer.getPrice()!=null)
            exist.setPrice(beer.getPrice());
        if(StringUtils.hasText(beer.getUpc()))
            exist.setUpc(beer.getUpc());
        if(beer.getQuantityOnHand()!=null)
            exist.setQuantityOnHand(beer.getQuantityOnHand());

        beerMap.put(id,exist);

        return Optional.of(exist);
    }
    @Override
    public Optional<BeerDTO> deleteById(UUID beerId) {

        BeerDTO deletedBeer = beerMap.remove(beerId);

        return Optional.of(deletedBeer);
    }

}
