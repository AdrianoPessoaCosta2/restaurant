package br.com.restaurants.core.usecases;

import br.com.restaurants.core.entities.Address;
import br.com.restaurants.core.entities.Restaurant;
import br.com.restaurants.core.entities.User;
import br.com.restaurants.core.gateway.AddressGateway;
import br.com.restaurants.core.gateway.RestaurantGateway;
import br.com.restaurants.core.gateway.UserGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateRestaurantUseCaseTest {

    @Mock
    private RestaurantGateway restaurantGateway;
    @Mock
    private AddressGateway addressGateway;
    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private CreateRestaurantUseCase useCase;

    @Test
    void shouldCreateRestaurantSuccessfully() {
        UUID ownerId = UUID.randomUUID();
        User owner = new User();
        owner.setPublicId(ownerId);

        Address address = new Address();
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);

        when(userGateway.findById(ownerId)).thenReturn(owner);
        when(addressGateway.save(any(Address.class))).thenReturn(address);
        when(restaurantGateway.save(any(Restaurant.class))).thenReturn(restaurant);

        Restaurant result = useCase.execute(restaurant, ownerId);

        assertNotNull(result);
        assertEquals(owner, restaurant.getUser());
        verify(userGateway).findById(ownerId);
        verify(addressGateway).save(address);
        verify(restaurantGateway).save(restaurant);
    }
}