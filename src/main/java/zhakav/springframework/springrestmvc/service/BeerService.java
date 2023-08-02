package zhakav.springframework.springrestmvc.service;

import zhakav.springframework.springrestmvc.model.Beer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    List<Beer> getAll();
    Optional<Beer> getByID(UUID id);
    Beer save(Beer beer);
    Optional<Beer> updateById(Beer beer , UUID id);
    Optional<Beer> patchById(Beer beer , UUID id);
    Optional<Beer> deleteById(UUID beerId);
}
