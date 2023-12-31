package zhakav.springframework.springrestmvc.service;

import zhakav.springframework.springrestmvc.model.BeerDTO;
import zhakav.springframework.springrestmvc.model.BeerStyle;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    List<BeerDTO> getAll(String beerName, BeerStyle beerStyle,
                         boolean showInventory, int pageNumber,
                         int pageSize);
    Optional<BeerDTO> getByID(UUID id);
    BeerDTO save(BeerDTO beer);
    Optional<BeerDTO> updateById(BeerDTO beer , UUID id);
    Optional<BeerDTO> patchById(BeerDTO beer , UUID id);
    Optional<BeerDTO> deleteById(UUID beerId);
}
