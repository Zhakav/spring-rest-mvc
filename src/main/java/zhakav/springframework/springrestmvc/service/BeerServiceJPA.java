package zhakav.springframework.springrestmvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import zhakav.springframework.springrestmvc.mapper.BeerMapper;
import zhakav.springframework.springrestmvc.model.BeerDTO;
import zhakav.springframework.springrestmvc.repository.BeerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> getAll() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDTO)
                .collect(Collectors.toList());
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
        return null;
    }

    @Override
    public Optional<BeerDTO> updateById(BeerDTO beer, UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<BeerDTO> patchById(BeerDTO beer, UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<BeerDTO> deleteById(UUID beerId) {
        return Optional.empty();
    }
}
