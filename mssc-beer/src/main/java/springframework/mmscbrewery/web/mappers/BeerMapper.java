package springframework.mmscbrewery.web.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import springframework.mmscbrewery.domain.Beer;
import springframework.mmscbrewery.web.model.BeerDto;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {
    BeerDto beerToBeerDto(Beer beer);

    BeerDto beerToBeerDtoWithInventory(Beer beer);
    Beer beerDtoToBeer(BeerDto beerDto);
}
