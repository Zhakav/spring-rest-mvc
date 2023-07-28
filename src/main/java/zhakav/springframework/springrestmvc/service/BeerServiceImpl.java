package zhakav.springframework.springrestmvc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zhakav.springframework.springrestmvc.model.Beer;
import zhakav.springframework.springrestmvc.model.BeerStyle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID,Beer> beerMap;
    public BeerServiceImpl(){

        beerMap=new HashMap<>();


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


    }
    @Override
    public List<Beer> getAll(){

        log.debug("GET ALL BEERS -IN BEER SERVICE ");

        return new ArrayList<>(beerMap.values());

    }
    @Override
    public Beer getByID(UUID id) {

        log.debug("GET BEER BY ID -IN BEER SERVICE -ID : " + id);

        return beerMap.get(id);
    }

    @Override
    public Beer save(Beer beer) {

        Beer savedBeer=Beer.builder()
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
    public Beer updateById(Beer beer, UUID id) {

        Beer exist = beerMap.get(id);

        if(beer.getBeerName()!=null)
            exist.setBeerName(beer.getBeerName());
        if(beer.getBeerStyle()!=null)
            exist.setBeerStyle(beer.getBeerStyle());
        if(beer.getPrice()!=null)
            exist.setPrice(beer.getPrice());
        if(beer.getUpc()!=null)
            exist.setUpc(beer.getUpc());
        if(beer.getQuantityOnHand()!=null)
            exist.setQuantityOnHand(beer.getQuantityOnHand());

        exist.setUpdateDate(LocalDateTime.now());

        beerMap.put(id,exist);

        return exist;
    }

    @Override
    public Beer deleteById(UUID beerId) {

        Beer deletedBeer = beerMap.remove(beerId);

        return deletedBeer;
    }

}
