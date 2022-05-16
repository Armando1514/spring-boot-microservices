package springframework.mmscbrewery.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import springframework.mmscbrewery.domain.Beer;

import java.util.UUID;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {
}
