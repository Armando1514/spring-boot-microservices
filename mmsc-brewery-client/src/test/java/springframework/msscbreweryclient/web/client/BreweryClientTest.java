package springframework.msscbreweryclient.web.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springframework.msscbreweryclient.web.model.BeerDto;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BreweryClientTest {

    @Autowired
    BreweryClient client;

    BeerDto beerDto;
    @BeforeEach
    void setup() {
        beerDto = BeerDto.builder()
                .beerStyle("LAGER")
                .beerName("peroni")
                .price(new BigDecimal("1.23"))
                .upc(13241214L)
                .build();
    }
    @Test
    void getBeerById() {
        BeerDto beerDto = client.getBeerById(UUID.randomUUID());
        assertNotNull(beerDto);
    }

    @Test
    void saveNewBeer() {
        URI uri = client.saveNewBeer(beerDto);
        assertNotNull(uri);
    }

    @Test
    void updateBeer() {
        client.updateBeer(UUID.randomUUID(), beerDto);
    }

    @Test
    void deleteBeer() {
        client.deleteBeer(UUID.randomUUID());
    }
}