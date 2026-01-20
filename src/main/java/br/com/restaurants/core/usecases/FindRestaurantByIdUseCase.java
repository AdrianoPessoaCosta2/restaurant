package br.com.restaurants.core.usecases;

import br.com.restaurants.core.entities.Restaurant;
import br.com.restaurants.core.gateway.RestaurantGateway;

import java.util.UUID;

public class FindRestaurantByIdUseCase {

    private final RestaurantGateway restaurantGateway;

    public FindRestaurantByIdUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public Restaurant execute(UUID id) {
        return restaurantGateway.findById(id);
    }
}