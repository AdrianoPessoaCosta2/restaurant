package br.com.restaurants.core.usecases;

import br.com.restaurants.core.entities.Address;
import br.com.restaurants.core.entities.Restaurant;
import br.com.restaurants.core.entities.User;
import br.com.restaurants.core.enums.TypeUser;
import br.com.restaurants.core.gateway.AddressGateway;
import br.com.restaurants.core.gateway.RestaurantGateway;
import br.com.restaurants.core.gateway.UserGateway;
import br.com.restaurants.infrastructure.exception.TypeUserException;

import java.util.UUID;

public class CreateRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;
    private final AddressGateway addressGateway;
    private final UserGateway userGateway;

    public CreateRestaurantUseCase(
            RestaurantGateway restaurantGateway,
            AddressGateway addressGateway,
            UserGateway userGateway
    ) {
        this.restaurantGateway = restaurantGateway;
        this.addressGateway = addressGateway;
        this.userGateway = userGateway;
    }

    public Restaurant execute(Restaurant restaurant, UUID ownerId) {
        User owner = userGateway.findById(ownerId);

        if(!TypeUser.RESTAURANT_OWNER.equals(owner.getTypeUser())){
            throw new TypeUserException("Somente donos de restaurantes podem cadastrar novos restaurantes.");
        }

        restaurant.setUser(owner);

        Address savedAddress = addressGateway.save(restaurant.getAddress());
        restaurant.setAddress(savedAddress);

        return restaurantGateway.save(restaurant);
    }
}