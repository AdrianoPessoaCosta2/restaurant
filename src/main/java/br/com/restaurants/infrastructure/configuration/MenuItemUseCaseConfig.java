package br.com.restaurants.infrastructure.configuration;

import br.com.restaurants.core.gateway.MenuItemGateway;
import br.com.restaurants.core.gateway.RestaurantGateway;
import br.com.restaurants.core.usecases.CreateMenuItemUseCase;
import br.com.restaurants.core.usecases.FindMenuItemsByRestaurantUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuItemUseCaseConfig {

    @Bean
    public CreateMenuItemUseCase createMenuItemUseCase(
            MenuItemGateway menuItemGateway,
            RestaurantGateway restaurantGateway) {
        return new CreateMenuItemUseCase(menuItemGateway, restaurantGateway);
    }

    @Bean
    public FindMenuItemsByRestaurantUseCase findMenuItemsByRestaurantUseCase(
            MenuItemGateway menuItemGateway) {
        return new FindMenuItemsByRestaurantUseCase(menuItemGateway);
    }
}