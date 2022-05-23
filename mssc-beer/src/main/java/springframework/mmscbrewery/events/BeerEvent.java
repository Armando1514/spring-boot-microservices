package springframework.mmscbrewery.events;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import springframework.mmscbrewery.web.model.BeerDto;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Builder
public class BeerEvent implements Serializable {
    static final long serialVersionUID = -5815566940065181210L;

    private final BeerDto beerDto;
}
