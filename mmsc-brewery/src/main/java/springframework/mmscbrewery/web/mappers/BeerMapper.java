package springframework.mmscbrewery.web.mappers;

import org.mapstruct.Mapper;
import springframework.mmscbrewery.domain.Beer;
import springframework.mmscbrewery.web.model.BeerDto;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
    BeerDto beerToBeerDto(Beer beer);

    Beer BeerDtoToBeer(BeerDto dto);
}
