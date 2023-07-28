package zhakav.springframework.springrestmvc.service;

import zhakav.springframework.springrestmvc.model.Beer;

import java.util.UUID;

public interface BeerService {
    Beer getByID(UUID id);
}
