package springframework.mmscbrewery.services.inventory;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springframework.mmscbrewery.web.services.inventory.BeerInventoryService;

import java.util.UUID;

@Disabled // utility for manual testing
@SpringBootTest
public class BeerInventoryServiceRestTemplateImpl {

    @Autowired
    BeerInventoryService beerInventoryService;

    @Test
    void getOnhandInventory() {

       Integer qoh = beerInventoryService.getOnhandInventory(UUID.fromString("0782ef48-a439-10"));

       System.out.println(qoh);

    }

}
