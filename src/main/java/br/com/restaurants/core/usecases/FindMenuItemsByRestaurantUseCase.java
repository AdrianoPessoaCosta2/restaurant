package br.com.restaurants.core.usecases;

import br.com.restaurants.core.entities.MenuItem;
import br.com.restaurants.core.gateway.MenuItemGateway;
import java.util.List;
import java.util.UUID;

public class FindMenuItemsByRestaurantUseCase {
    private final MenuItemGateway menuItemGateway;

    public FindMenuItemsByRestaurantUseCase(MenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public List<MenuItem> execute(UUID restaurantId) {
        return menuItemGateway.findAllByRestaurantId(restaurantId);
    }
}