package springframework.mmscbrewery.web.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springframework.mmscbrewery.web.model.BeerDto;

import java.util.UUID;

@Service
public class BeerServiceImpl implements BeerService{

    @Override
    public BeerDto getBeerById(UUID beerId) {
        return BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("Galaxy cat")
                .build();
    }

    @Override
    public BeerDto saveBeer(BeerDto beerDto) {
        return beerDto;
    }

    @Override
    public void updateBeer(UUID beerId, BeerDto beerDto) {
        // Update Beer
    }

    @Override
    public void deleteById(UUID beerId) {
        // Delete Beer
    }
}
