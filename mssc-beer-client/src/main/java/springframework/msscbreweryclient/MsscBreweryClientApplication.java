package springframework.msscbreweryclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MsscBreweryClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsscBreweryClientApplication.class, args);
    }

}
