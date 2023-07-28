package zhakav.springframework.springrestmvc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    @GetMapping
    public List<Beer> getAll(){

        log.debug("GET ALL BEERS -IN BEER CONTROLLER ");

        return beerService.getAll();

    }
    @GetMapping(value = "/{beerId}")
    public Beer getByID(@PathVariable("beerId") UUID beerId){

        log.debug("GET BEER BY ID -IN BEER CONTROLLER -ID : " + beerId);

        return beerService.getByID(beerId);

    }

    @PostMapping
    public ResponseEntity<Beer> save(@RequestBody Beer beer){

        Beer savedBeer=beerService.save(beer);

        HttpHeaders headers=new HttpHeaders();
        headers.add("Location","/api/v1/beer/" + savedBeer.getId());

        return new ResponseEntity<>(headers,HttpStatus.CREATED);

    }
}
