package springframework.mmscbrewery.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springframework.mmscbrewery.domain.Beer;
import springframework.mmscbrewery.repositories.BeerRepository;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) {
        loadBeerObjects();
    }

    private void loadBeerObjects(){
        if(beerRepository.count() == 0) {
            Beer lol = beerRepository.save(Beer.builder()
                    .beerName("Mango Bobs")
                    .beerStyle("IPA")
                    .quantityToBrew(200)
                    .quantityOnHand(12)
                    .upc(BEER_1_UPC)
                    .price(new BigDecimal("12.95"))
                    .build());
            beerRepository.save(Beer.builder()
                    .beerName("Peroni")
                    .beerStyle("LAGER")
                    .quantityToBrew(600)
                    .quantityOnHand(52)
                    .upc(BEER_2_UPC)
                    .price(new BigDecimal("10.05"))
                    .build());
            beerRepository.save(Beer.builder()
                    .beerName("Ichnusa")
                    .beerStyle("PALE_ALE")
                    .quantityToBrew(100)
                    .quantityOnHand(62)
                    .upc(BEER_3_UPC)
                    .price(new BigDecimal("20.05"))
                    .build());
        }
    }
}
