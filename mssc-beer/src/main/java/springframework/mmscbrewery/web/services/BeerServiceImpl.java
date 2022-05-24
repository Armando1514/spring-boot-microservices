package springframework.mmscbrewery.web.services;

import brewery.model.BeerDto;
import brewery.model.BeerPagedList;
import brewery.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import springframework.mmscbrewery.domain.Beer;
import springframework.mmscbrewery.repositories.BeerRepository;
import springframework.mmscbrewery.web.controller.exception.NotFoundException;
import springframework.mmscbrewery.web.mappers.BeerMapper;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService{

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false ")
    @Override
    public BeerDto getBeerById(UUID beerId, Boolean showInventoryOnHand) {
        if (showInventoryOnHand) {
            return beerMapper.beerToBeerDtoWithInventory(
                    beerRepository.findById(beerId)
                            .orElseThrow(() -> new NotFoundException(beerId + ", Not Found."))
            );
        }
        else {
            return beerMapper.beerToBeerDto(
                    beerRepository.findById(beerId)
                            .orElseThrow(() -> new NotFoundException(beerId + ", Not Found."))
            );
        }
    }

    @Cacheable(cacheNames = "beerUpcCache")
    @Override
    public BeerDto getByUpc(String upc) {
        return beerMapper.beerToBeerDto(beerRepository.findByUpc(upc));
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
    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false")
    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {

        BeerPagedList beerPagedList = null;
        Page<Beer> beerPage;

        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            //search beer_service name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search beer_service style
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if(showInventoryOnHand){
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map((beer) -> beerMapper.beerToBeerDtoWithInventory(beer))
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());


        } else {
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map((beer) -> beerMapper.beerToBeerDto(beer))
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        }

        return beerPagedList;
    }
}
