package br.com.restaurants.core.gateway;

import br.com.restaurants.core.entities.Restaurant;
import java.util.List;
import java.util.UUID;

public interface RestaurantGateway {
    Restaurant save(Restaurant restaurant);
    Restaurant update(Restaurant restaurant);
    List<Restaurant> findAll();
    Restaurant findById(UUID id);
    void deleteById(UUID id);
}