package mssc.brewery.msscbrewerygateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalHostRouteConfig {

    @Bean
    public RouteLocator localHostRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/api/v1/beer*", "/api/v1/beer/*", "/api/v1/beerUpc/*")
                        .uri("http://localhost:8080"))
                .route(r -> r.path("/api/v1/customers/**")
                        .uri("http://localhost:8081"))
                .route(r -> r.path("/api/v1/beer/*/inventory")
                        .uri("http://localhost:8082"))
                .build();
    }
}
