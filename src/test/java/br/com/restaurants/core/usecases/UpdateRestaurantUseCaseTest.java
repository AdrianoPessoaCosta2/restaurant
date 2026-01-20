package br.com.restaurants.core.usecases;

import br.com.restaurants.core.entities.Restaurant;
import br.com.restaurants.core.gateway.RestaurantGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateRestaurantUseCaseTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private UpdateRestaurantUseCase useCase;

    @Test
    void shouldUpdateRestaurant() {
        Restaurant restaurant = new Restaurant();
        Restaurant updatedRestaurant = new Restaurant();

        when(restaurantGateway.update(restaurant)).thenReturn(updatedRestaurant);

        Restaurant result = useCase.execute(restaurant);

        assertEquals(updatedRestaurant, result);
        verify(restaurantGateway).update(restaurant);
    }
}