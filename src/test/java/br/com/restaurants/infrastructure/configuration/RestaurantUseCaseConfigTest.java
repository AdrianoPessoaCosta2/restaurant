package br.com.restaurants.infrastructure.configuration;

import br.com.restaurants.core.gateway.AddressGateway;
import br.com.restaurants.core.gateway.RestaurantGateway;
import br.com.restaurants.core.gateway.UserGateway;
import br.com.restaurants.core.usecases.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseConfigTest {

    private final RestaurantUseCaseConfig config = new RestaurantUseCaseConfig();

    @Mock
    private RestaurantGateway restaurantGateway;

    @Mock
    private AddressGateway addressGateway;

    @Mock
    private UserGateway userGateway;

    @Test
    void shouldCreateCreateRestaurantUseCase() {
        CreateRestaurantUseCase useCase = config.createRestaurantUseCase(restaurantGateway, addressGateway, userGateway);
        assertNotNull(useCase);
    }

    @Test
    void shouldCreateFindAllRestaurantsUseCase() {
        FindAllRestaurantsUseCase useCase = config.findAllRestaurantsUseCase(restaurantGateway);
        assertNotNull(useCase);
    }

    @Test
    void shouldCreateFindRestaurantByIdUseCase() {
        FindRestaurantByIdUseCase useCase = config.findRestaurantByIdUseCase(restaurantGateway);
        assertNotNull(useCase);
    }

    @Test
    void shouldCreateUpdateRestaurantUseCase() {
        UpdateRestaurantUseCase useCase = config.updateRestaurantUseCase(restaurantGateway);
        assertNotNull(useCase);
    }

    @Test
    void shouldCreateDeleteRestaurantUseCase() {
        DeleteRestaurantUseCase useCase = config.deleteRestaurantUseCase(restaurantGateway);
        assertNotNull(useCase);
    }
}