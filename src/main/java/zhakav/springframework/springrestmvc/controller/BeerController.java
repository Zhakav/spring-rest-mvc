package zhakav.springframework.springrestmvc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zhakav.springframework.springrestmvc.exception.NotFoundException;
import zhakav.springframework.springrestmvc.model.BeerDTO;
import zhakav.springframework.springrestmvc.model.BeerStyle;
import zhakav.springframework.springrestmvc.service.BeerService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@RestController
@RequiredArgsConstructor
public class BeerController {
    private final BeerService beerService;

    public static final String PATH= "/api/v1/beer";
    public static final String PATH_ID= PATH + "/{beerId}";
    @GetMapping(PATH)
    public List<BeerDTO> getAll(@RequestParam(required = false) String beerName ,
                                @RequestParam(required = false) BeerStyle beerStyle,
                                @RequestParam(required = false) boolean showInventory,
                                @RequestParam(required = false)int pageNumber,
                                @RequestParam(required = false)int pageSize){

        log.debug("GET ALL BEERS -IN BEER CONTROLLER ");

        return beerService.getAll(beerName,beerStyle,showInventory, pageNumber, pageSize);

    }
    @GetMapping(PATH_ID)
    public BeerDTO getByID(@PathVariable("beerId") UUID beerId){

        log.debug("GET BEER BY ID -IN BEER CONTROLLER -ID : " + beerId);

        return beerService.getByID(beerId).orElseThrow(NotFoundException::new);

    }
    @PostMapping(PATH)
    public ResponseEntity<BeerDTO> save(@Valid @RequestBody BeerDTO beer){

        BeerDTO savedBeer=beerService.save(beer);

        HttpHeaders headers=new HttpHeaders();
        headers.add("Location","/api/v1/beer/" + savedBeer.getId());

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(savedBeer);

    }
    @PutMapping(PATH_ID)
    public ResponseEntity<BeerDTO> updateById(
            @PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer){

        Optional<BeerDTO> response=beerService.updateById(beer,beerId);

        return new ResponseEntity<>(response.get(),HttpStatus.ACCEPTED);

    }
    @PatchMapping(PATH_ID)
    public ResponseEntity<BeerDTO> patchById(
            @PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer){

        return new ResponseEntity<>(beerService.patchById(beer,beerId).orElseThrow(NotFoundException::new),HttpStatus.ACCEPTED);

    }
    @DeleteMapping(PATH_ID)
    public ResponseEntity<BeerDTO> deleteById(@PathVariable("beerId") UUID beerId){

        return new ResponseEntity<>(beerService.deleteById(beerId).orElseThrow(NotFoundException::new),HttpStatus.NO_CONTENT);


    }

}
