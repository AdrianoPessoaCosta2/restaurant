package br.com.restaurants.infrastructure.configuration;

import br.com.restaurants.core.gateway.AddressGateway;
import br.com.restaurants.core.gateway.RestaurantGateway;
import br.com.restaurants.core.gateway.UserGateway;
import br.com.restaurants.core.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantUseCaseConfig {

    @Bean
    public CreateRestaurantUseCase createRestaurantUseCase(
            RestaurantGateway restaurantGateway,
            AddressGateway addressGateway,
            UserGateway userGateway
    ) {
        return new CreateRestaurantUseCase(restaurantGateway, addressGateway, userGateway);
    }

    @Bean
    public FindAllRestaurantsUseCase findAllRestaurantsUseCase(RestaurantGateway restaurantGateway) {
        return new FindAllRestaurantsUseCase(restaurantGateway);
    }

    @Bean
    public FindRestaurantByIdUseCase findRestaurantByIdUseCase(RestaurantGateway restaurantGateway) {
        return new FindRestaurantByIdUseCase(restaurantGateway);
    }

    @Bean
    public UpdateRestaurantUseCase updateRestaurantUseCase(RestaurantGateway restaurantGateway) {
        return new UpdateRestaurantUseCase(restaurantGateway);
    }

    @Bean
    public DeleteRestaurantUseCase deleteRestaurantUseCase(RestaurantGateway restaurantGateway) {
        return new DeleteRestaurantUseCase(restaurantGateway);
    }
}