package br.com.restaurants.core.gateway;

import br.com.restaurants.core.entities.MenuItem;
import java.util.List;
import java.util.UUID;

public interface MenuItemGateway {
    MenuItem save(MenuItem menuItem);
    MenuItem update(MenuItem menuItem);
    List<MenuItem> findAllByRestaurantId(UUID restaurantId);
    MenuItem findById(UUID id);
    void deleteById(UUID id);
}