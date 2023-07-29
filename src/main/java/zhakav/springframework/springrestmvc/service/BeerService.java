package zhakav.springframework.springrestmvc.service;

import zhakav.springframework.springrestmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    List<Beer> getAll();
    Beer getByID(UUID id);
    Beer save(Beer beer);
    Beer updateById(Beer beer , UUID id);
    Beer patchById(Beer beer , UUID id);
    Beer deleteById(UUID beerId);
}
