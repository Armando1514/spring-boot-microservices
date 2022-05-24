package sfg.beer.order.service.services;

import sfg.beer.order.service.domain.BeerOrder;

public interface BeerOrderManager {

    BeerOrder newBeerOrder(BeerOrder beerOrder);
}
