package br.com.restaurants.core.usecases;

import br.com.restaurants.core.entities.Address;
import br.com.restaurants.core.entities.User;
import br.com.restaurants.core.gateway.AddressGateway;
import br.com.restaurants.core.gateway.UserGateway;

public class CreateUserUseCase {

    private final UserGateway userGateway;
    private final AddressGateway addressGateway;

    public CreateUserUseCase(
            UserGateway userGateway,
            AddressGateway addressGateway
    ) {
        this.userGateway = userGateway;
        this.addressGateway = addressGateway;
    }

    public User execute(User user) {
        user.setPassword(user.getPassword());

        Address savedAddress = addressGateway.save(user.getAddress());
        user.setAddress(savedAddress);

        return userGateway.save(user);
    }
}
