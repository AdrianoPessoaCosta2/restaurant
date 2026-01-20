package br.com.restaurants.core.usecases;

import br.com.restaurants.core.entities.Restaurant;
import br.com.restaurants.core.gateway.RestaurantGateway;

import java.util.List;

public class FindAllRestaurantsUseCase {

    private final RestaurantGateway restaurantGateway;

    public FindAllRestaurantsUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public List<Restaurant> execute() {
        return restaurantGateway.findAll();
    }
}