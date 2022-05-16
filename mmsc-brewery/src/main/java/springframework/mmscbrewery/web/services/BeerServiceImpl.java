package springframework.mmscbrewery.web.services;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springframework.mmscbrewery.domain.Beer;
import springframework.mmscbrewery.repositories.BeerRepository;
import springframework.mmscbrewery.web.controller.exception.NotFoundException;
import springframework.mmscbrewery.web.mappers.BeerMapper;
import springframework.mmscbrewery.web.model.BeerDto;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService{

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerDto getBeerById(UUID beerId) {

        return beerMapper.beerToBeerDto(
                beerRepository.findById(beerId)
                        .orElseThrow(() -> new NotFoundException(beerId + ", Not Found."))
        );
    }

    @Override
    public BeerDto saveBeer(BeerDto beerDto) {

        return beerMapper.beerToBeerDto(
               beerRepository.save(
                       beerMapper.beerDtoToBeer(beerDto)
               )
        );
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(
                () -> new NotFoundException(beerId + ", Not Found.")
        );

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }

    @Override
    public void deleteById(UUID beerId) {
        // Delete Beer
    }
}
