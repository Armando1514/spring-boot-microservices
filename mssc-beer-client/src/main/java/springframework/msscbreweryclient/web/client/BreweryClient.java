package springframework.msscbreweryclient.web.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import springframework.msscbreweryclient.web.model.BeerDto;
import springframework.msscbreweryclient.web.model.BeerStyleEnum;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.UUID;

@Component
@ConfigurationProperties(value = "sfg.brewery", ignoreUnknownFields = false)
public class BreweryClient {

    public final String BEER_PATH_V1 = "/api/v1/beer/";


    private  String apiHost;
    private final RestTemplate restTemplate;

    public BreweryClient(RestTemplateBuilder restTemplateBuilder) {

        this.restTemplate = restTemplateBuilder.build();
    }


    @Transactional
    @Scheduled(fixedRate = 1000)
    public void callRegularlyBeerService(){

        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        BeerDto requestSaveBeer  = BeerDto.builder()
                .beerStyle(BeerStyleEnum.ALE)
                .beerName(generatedString)
                .quantityOnHand(10)
                .quantityToBrew(2)
                .price(new BigDecimal("1.23"))
                .upc(String.valueOf(UUID.randomUUID()))
                .build();

        BeerDto responseBeerSaved  = this.saveNewBeer(requestSaveBeer);

        responseBeerSaved = this.getBeerById(responseBeerSaved.getId());

        responseBeerSaved.setBeerName("newName-"+ generatedString);

        this.updateBeer(responseBeerSaved.getId(), responseBeerSaved);
    }

    public BeerDto getBeerById(UUID uuid) {

        return restTemplate.getForObject(apiHost + BEER_PATH_V1 + uuid, BeerDto.class);
    }



    public BeerDto saveNewBeer(BeerDto beerDto){

            return restTemplate.postForObject(apiHost + BEER_PATH_V1, beerDto, BeerDto.class);
    }


    public void updateBeer(UUID uuid, BeerDto beerDto){
        restTemplate.put(apiHost + BEER_PATH_V1 + uuid, beerDto);
    }


    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }
}
