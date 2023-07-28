package zhakav.springframework.springrestmvc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import zhakav.springframework.springrestmvc.model.Beer;
import zhakav.springframework.springrestmvc.service.BeerService;

import java.util.List;
import java.util.UUID;
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/beer")
public class BeerController {
    private BeerService beerService;
    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> getAll(){

        log.debug("GET ALL BEERS -IN BEER CONTROLLER ");

        return beerService.getAll();

    }
    @RequestMapping(value = "/{beerId}", method = RequestMethod.GET)
    public Beer getByID(@PathVariable("beerId") UUID beerId){

        log.debug("GET BEER BY ID -IN BEER CONTROLLER -ID : " + beerId);

        return beerService.getByID(beerId);

    }
}
