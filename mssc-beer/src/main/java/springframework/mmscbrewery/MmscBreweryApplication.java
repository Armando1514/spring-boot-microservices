package springframework.mmscbrewery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MmscBreweryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MmscBreweryApplication.class, args);
	}

}
