package zhakav.springframework.springrestmvc.mapper;

import org.mapstruct.Mapper;
import zhakav.springframework.springrestmvc.entity.Beer;
import zhakav.springframework.springrestmvc.model.BeerDTO;

@Mapper
public interface BeerMapper {

    Beer beerDTOToBeer(BeerDTO beerDTO);

    BeerDTO beerToBeerDTO(Beer beer);

}
