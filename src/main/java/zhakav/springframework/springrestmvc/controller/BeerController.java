package zhakav.springframework.springrestmvc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import zhakav.springframework.springrestmvc.model.Beer;
import zhakav.springframework.springrestmvc.service.BeerService;

import java.util.UUID;
@Slf4j
@Controller
@AllArgsConstructor
public class BeerController {
    private BeerService beerService;

    Beer getByID(UUID id){

        log.debug("GET BEER BY ID -IN BEER CONTROLLER -ID : " + id);

        return beerService.getByID(id);

    }
}
