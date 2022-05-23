package springframework.mmscbrewery.events;

import lombok.*;
import springframework.mmscbrewery.web.model.BeerDto;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BeerEvent implements Serializable {
    static final long serialVersionUID = -5815566940065181210L;

    private BeerDto beerDto;
}
