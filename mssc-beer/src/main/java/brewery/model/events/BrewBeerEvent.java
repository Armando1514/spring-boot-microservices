package brewery.model.events;

import brewery.model.BeerDto;
import lombok.NoArgsConstructor;
@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {

    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
