package br.com.restaurants.core.usecases;

import br.com.restaurants.core.entities.Restaurant;
import br.com.restaurants.core.gateway.RestaurantGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindRestaurantByIdUseCaseTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private FindRestaurantByIdUseCase useCase;

    @Test
    void shouldFindRestaurantById() {
        UUID id = UUID.randomUUID();
        Restaurant expected = new Restaurant();

        when(restaurantGateway.findById(id)).thenReturn(expected);

        Restaurant result = useCase.execute(id);

        assertEquals(expected, result);
        verify(restaurantGateway).findById(id);
    }
}