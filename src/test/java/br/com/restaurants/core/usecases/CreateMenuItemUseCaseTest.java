package br.com.restaurants.core.usecases;

import br.com.restaurants.core.entities.MenuItem;
import br.com.restaurants.core.entities.Restaurant;
import br.com.restaurants.core.gateway.MenuItemGateway;
import br.com.restaurants.core.gateway.RestaurantGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateMenuItemUseCaseTest {

    @Mock
    private MenuItemGateway menuItemGateway;
    @Mock
    private RestaurantGateway restaurantGateway;
    @InjectMocks
    private CreateMenuItemUseCase useCase;

    @Test
    void shouldCreateMenuItemSuccessfully() {
        UUID restaurantId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant();
        MenuItem menuItem = new MenuItem();
        MenuItem savedItem = new MenuItem();

        when(restaurantGateway.findById(restaurantId)).thenReturn(restaurant);
        when(menuItemGateway.save(menuItem)).thenReturn(savedItem);

        MenuItem result = useCase.execute(menuItem, restaurantId);

        assertNotNull(result);
        assertEquals(restaurant, menuItem.getRestaurant());
        verify(restaurantGateway).findById(restaurantId);
        verify(menuItemGateway).save(menuItem);
    }
}