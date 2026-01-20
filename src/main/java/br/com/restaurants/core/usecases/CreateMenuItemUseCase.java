package br.com.restaurants.core.usecases;

import br.com.restaurants.core.entities.MenuItem;
import br.com.restaurants.core.entities.Restaurant;
import br.com.restaurants.core.gateway.MenuItemGateway;
import br.com.restaurants.core.gateway.RestaurantGateway;
import java.util.UUID;

public class CreateMenuItemUseCase {
    private final MenuItemGateway menuItemGateway;
    private final RestaurantGateway restaurantGateway;

    public CreateMenuItemUseCase(MenuItemGateway menuItemGateway, RestaurantGateway restaurantGateway) {
        this.menuItemGateway = menuItemGateway;
        this.restaurantGateway = restaurantGateway;
    }

    public MenuItem execute(MenuItem menuItem, UUID restaurantId) {
        Restaurant restaurant = restaurantGateway.findById(restaurantId);
        menuItem.setRestaurant(restaurant);
        return menuItemGateway.save(menuItem);
    }
}