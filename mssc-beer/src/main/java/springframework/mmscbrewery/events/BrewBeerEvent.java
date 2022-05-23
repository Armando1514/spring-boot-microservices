package springframework.mmscbrewery.events;

import lombok.NoArgsConstructor;
import springframework.mmscbrewery.web.model.BeerDto;
@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent{

    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
