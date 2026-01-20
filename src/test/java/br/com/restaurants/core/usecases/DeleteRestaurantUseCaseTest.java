package br.com.restaurants.core.usecases;

import br.com.restaurants.core.gateway.RestaurantGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteRestaurantUseCaseTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private DeleteRestaurantUseCase useCase;

    @Test
    void shouldDeleteRestaurant() {
        UUID id = UUID.randomUUID();

        useCase.execute(id);

        verify(restaurantGateway).deleteById(id);
    }
}