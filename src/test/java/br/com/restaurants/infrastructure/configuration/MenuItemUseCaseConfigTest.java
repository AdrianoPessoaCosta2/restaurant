package br.com.restaurants.infrastructure.configuration;

import br.com.restaurants.core.gateway.MenuItemGateway;
import br.com.restaurants.core.gateway.RestaurantGateway;
import br.com.restaurants.core.usecases.CreateMenuItemUseCase;
import br.com.restaurants.core.usecases.FindMenuItemsByRestaurantUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class MenuItemUseCaseConfigTest {

    private final MenuItemUseCaseConfig config = new MenuItemUseCaseConfig();

    @Mock
    private MenuItemGateway menuItemGateway;

    @Mock
    private RestaurantGateway restaurantGateway;

    @Test
    void shouldCreateCreateMenuItemUseCase() {
        CreateMenuItemUseCase useCase = config.createMenuItemUseCase(menuItemGateway, restaurantGateway);
        assertNotNull(useCase);
    }

    @Test
    void shouldCreateFindMenuItemsByRestaurantUseCase() {
        FindMenuItemsByRestaurantUseCase useCase = config.findMenuItemsByRestaurantUseCase(menuItemGateway);
        assertNotNull(useCase);
    }
}