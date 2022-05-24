package springframework.mmscbrewery.web.services.brewing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import springframework.mmscbrewery.config.JmsConfig;
import springframework.mmscbrewery.domain.Beer;
import common.events.BrewBeerEvent;
import springframework.mmscbrewery.repositories.BeerRepository;
import springframework.mmscbrewery.web.mappers.BeerMapper;
import springframework.mmscbrewery.web.services.inventory.BeerInventoryService;
import org.springframework.jms.core.JmsTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {
    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000) // every 5 seconds
    public void checkForLowInventory(){
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            Integer inventoryQOH = beerInventoryService.getOnhandInventory(beer.getId());
            log.debug("Min OnHand is: " + beer.getQuantityOnHand());
            log.debug("Inventory is: " + inventoryQOH);

            if(beer.getQuantityOnHand() >= inventoryQOH) {
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
            }
        });
    }
}
