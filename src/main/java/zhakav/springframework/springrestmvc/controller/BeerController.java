package zhakav.springframework.springrestmvc.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class BeerController {
    private final BeerService beerService;

    public static final String PATH= "/api/v1/beer";
    public static final String PATH_ID= PATH + "/{beerId}";
    @GetMapping(PATH)
    public List<Beer> getAll(){

        log.debug("GET ALL BEERS -IN BEER CONTROLLER ");

        return beerService.getAll();

    }
    @GetMapping(PATH_ID)
    public Beer getByID(@PathVariable("beerId") UUID beerId){

        log.debug("GET BEER BY ID -IN BEER CONTROLLER -ID : " + beerId);

        return beerService.getByID(beerId);

    }
    @PostMapping(PATH)
    public ResponseEntity<Beer> save(@RequestBody Beer beer){

        Beer savedBeer=beerService.save(beer);

        HttpHeaders headers=new HttpHeaders();
        headers.add("Location","/api/v1/beer/" + savedBeer.getId());

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(savedBeer);

    }
    @PutMapping(PATH_ID)
    public ResponseEntity<Beer> updateById(
            @PathVariable("beerId") UUID beerId, @RequestBody Beer beer){

        return new ResponseEntity<>(beerService.updateById(beer,beerId),HttpStatus.ACCEPTED);

    }
    @PatchMapping(PATH_ID)
    public ResponseEntity<Beer> patchById(
            @PathVariable("beerId") UUID beerId, @RequestBody Beer beer){

        return new ResponseEntity<>(beerService.patchById(beer,beerId),HttpStatus.ACCEPTED);

    }
    @DeleteMapping(PATH_ID)
    public ResponseEntity<Beer> deleteById(@PathVariable("beerId") UUID beerId){

        return new ResponseEntity<>(beerService.deleteById(beerId),HttpStatus.NO_CONTENT);


    }

}
