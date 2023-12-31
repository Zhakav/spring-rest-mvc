package zhakav.springframework.springrestmvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import zhakav.springframework.springrestmvc.entity.Beer;
import zhakav.springframework.springrestmvc.exception.NotFoundException;
import zhakav.springframework.springrestmvc.mapper.BeerMapper;
import zhakav.springframework.springrestmvc.model.BeerDTO;
import zhakav.springframework.springrestmvc.model.BeerStyle;
import zhakav.springframework.springrestmvc.repository.BeerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> getAll(String beerName, BeerStyle beerStyle, boolean showInventory, int pageNumber, int pageSize) {

        List<Beer> beerList;

        if (StringUtils.hasText(beerName) && beerStyle==null){

            beerList=getByBeerName(beerName);

        } else if (!StringUtils.hasText(beerName) && beerStyle!=null) {

            beerList=getByBeerStyle(beerStyle);

        } else if (StringUtils.hasText(beerName) && beerStyle!=null) {

            beerList=getByNameAndStyle(beerName,beerStyle);

        } else {

            beerList=beerRepository.findAll();

        }

        if(!showInventory){

            beerList.forEach(beer->{
                beer.setQuantityOnHand(null);
            });
        }

        return beerList
                .stream()
                .map(beerMapper::beerToBeerDTO)
                .collect(Collectors.toList());
    }

    List<Beer> getByBeerName(String beerName){

        return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%"+beerName+"%");

    }
    List<Beer> getByBeerStyle(BeerStyle beerStyle){

        return beerRepository.findAllByBeerStyle(beerStyle);

    }

    List<Beer> getByNameAndStyle(String beerName,BeerStyle beerStyle){

        return beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%"+beerName+"%",beerStyle);

    }

    @Override
    public Optional<BeerDTO> getByID(UUID id) {

        return Optional.ofNullable(
                beerMapper.beerToBeerDTO(
                        beerRepository.findById(id)
                                .orElse(null)));
    }

    @Override
    public BeerDTO save(BeerDTO beer) {

        return beerMapper.beerToBeerDTO(
                beerRepository.save(beerMapper.beerDTOToBeer(beer))) ;
    }

    @Override
    public Optional<BeerDTO> updateById(BeerDTO beer, UUID id) {

        AtomicReference<Optional<BeerDTO>> atomicReference= new AtomicReference<>();

        beerRepository.findById(id).ifPresentOrElse(foundBeer->{

         foundBeer.setBeerName(beer.getBeerName());
         foundBeer.setBeerStyle(beer.getBeerStyle());
         foundBeer.setUpc(beer.getUpc());
         foundBeer.setPrice(beer.getPrice());
         foundBeer.setQuantityOnHand(beer.getQuantityOnHand());

         atomicReference.set(Optional.of(beerMapper.beerToBeerDTO(beerRepository.save(foundBeer))));
        },()->{
            throw new NotFoundException();
        });

        return Optional.ofNullable(
                beerMapper.beerToBeerDTO(
                        beerRepository.findById(id).get()));
    }

    @Override
    public Optional<BeerDTO> patchById(BeerDTO beer, UUID id) {

        AtomicReference<Optional<BeerDTO>> atomicReference= new AtomicReference<>();

        beerRepository.findById(id).ifPresentOrElse(foundBeer->{

            if(beer.getBeerName()!=null)
                foundBeer.setBeerName(beer.getBeerName());
            if(beer.getBeerStyle()!=null)
                foundBeer.setBeerStyle(beer.getBeerStyle());
            if(beer.getPrice()!=null)
                foundBeer.setPrice(beer.getPrice());
            if(StringUtils.hasText(beer.getUpc()))
                foundBeer.setUpc(beer.getUpc());
            if(beer.getQuantityOnHand()!=null)
                foundBeer.setQuantityOnHand(beer.getQuantityOnHand());

            atomicReference.set(Optional.of(
                    beerMapper.beerToBeerDTO(
                            beerRepository.save(foundBeer))));

        },()->{

            throw new NotFoundException();

        });

        return Optional.ofNullable(
                beerMapper.beerToBeerDTO(
                        beerRepository.findById(id).get()));
    }

    @Override
    public Optional<BeerDTO> deleteById(UUID beerId) {

        Optional<BeerDTO> beerDTO= Optional.ofNullable(beerMapper.beerToBeerDTO(beerRepository.findById(beerId).orElseThrow(() -> {
            throw new NotFoundException();
        })));

        beerRepository.deleteById(beerId);

        return beerDTO;
    }
}
