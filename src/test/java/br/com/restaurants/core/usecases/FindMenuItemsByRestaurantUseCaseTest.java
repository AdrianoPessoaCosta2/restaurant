package br.com.restaurants.core.usecases;

import br.com.restaurants.core.entities.MenuItem;
import br.com.restaurants.core.gateway.MenuItemGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindMenuItemsByRestaurantUseCaseTest {

    @Mock
    private MenuItemGateway menuItemGateway;

    @InjectMocks
    private FindMenuItemsByRestaurantUseCase useCase;

    @Test
    void shouldFindMenuItemsByRestaurant() {
        UUID restaurantId = UUID.randomUUID();
        MenuItem item = new MenuItem();
        List<MenuItem> expected = List.of(item);

        when(menuItemGateway.findAllByRestaurantId(restaurantId)).thenReturn(expected);

        List<MenuItem> result = useCase.execute(restaurantId);

        assertEquals(expected, result);
        verify(menuItemGateway).findAllByRestaurantId(restaurantId);
    }
}