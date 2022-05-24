package common.events;

import common.events.BeerEvent;
import lombok.NoArgsConstructor;
import springframework.mmscbrewery.web.model.BeerDto;
@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {

    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
