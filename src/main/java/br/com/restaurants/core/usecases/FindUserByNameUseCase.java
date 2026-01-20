package br.com.restaurants.core.usecases;

import br.com.restaurants.core.entities.User;
import br.com.restaurants.core.gateway.UserGateway;

public class FindUserByNameUseCase {

    private final UserGateway userGateway;

    public FindUserByNameUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(String name) {
        return userGateway.findByName(name);
    }
}
