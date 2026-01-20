package br.com.restaurants.core.usecases;

import br.com.restaurants.core.gateway.RestaurantGateway;

import java.util.UUID;

public class DeleteRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;

    public DeleteRestaurantUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public void execute(UUID id) {
        restaurantGateway.deleteById(id);
    }
}