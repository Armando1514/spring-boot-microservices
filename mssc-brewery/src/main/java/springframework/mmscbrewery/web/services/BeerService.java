package springframework.mmscbrewery.web.services;

import org.springframework.data.domain.PageRequest;
import springframework.mmscbrewery.web.model.BeerDto;
import springframework.mmscbrewery.web.model.BeerPagedList;
import springframework.mmscbrewery.web.model.BeerStyleEnum;

import java.util.UUID;

public interface BeerService {
    BeerDto getBeerById(UUID beerId, Boolean showInventoryOnHand);

    BeerDto saveBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest of, Boolean showInventoryOnHand);
}
