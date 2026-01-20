package br.com.restaurants.core.usecases;

import br.com.restaurants.core.entities.Restaurant;
import br.com.restaurants.core.gateway.RestaurantGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllRestaurantsUseCaseTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private FindAllRestaurantsUseCase useCase;

    @Test
    void shouldReturnAllRestaurants() {
        Restaurant restaurant = new Restaurant();
        List<Restaurant> expectedList = List.of(restaurant);

        when(restaurantGateway.findAll()).thenReturn(expectedList);

        List<Restaurant> result = useCase.execute();

        assertEquals(expectedList, result);
        verify(restaurantGateway).findAll();
    }
}