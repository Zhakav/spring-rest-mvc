package zhakav.springframework.springrestmvc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zhakav.springframework.springrestmvc.model.Beer;
import zhakav.springframework.springrestmvc.service.BeerService;

import java.util.List;
import java.util.UUID;
@Slf4j
@RestController
@AllArgsConstructor
public class BeerController {
    private BeerService beerService;
    @RequestMapping("/api/v1/beer")
    public List<Beer> getAll(){

        return beerService.getAll();

    }
    @RequestMapping("")
    public Beer getByID(UUID id){

        log.debug("GET BEER BY ID -IN BEER CONTROLLER -ID : " + id);

        return beerService.getByID(id);

    }
}
