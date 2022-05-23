package springframework.mmscbrewery.web.services.brewing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import springframework.mmscbrewery.config.JmsConfig;
import springframework.mmscbrewery.domain.Beer;
import springframework.mmscbrewery.events.BrewBeerEvent;
import springframework.mmscbrewery.events.NewInventoryEvent;
import springframework.mmscbrewery.repositories.BeerRepository;
import springframework.mmscbrewery.web.model.BeerDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent event){
        BeerDto beerDto = event.getBeerDto();

        Beer beer = beerRepository.getOne(beerDto.getId());

        beerDto.setQuantityOnHand(beer.getQuantityToBrew());

        NewInventoryEvent  newInventoryEvent = new NewInventoryEvent(beerDto);

        log.debug("Brewed beer " + beer.getQuantityOnHand() +", QOH: " +beerDto.getQuantityOnHand());

        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
    }
}
