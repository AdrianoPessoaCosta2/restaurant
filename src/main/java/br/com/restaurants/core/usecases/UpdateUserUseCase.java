package br.com.restaurants.core.usecases;

import br.com.restaurants.core.entities.User;
import br.com.restaurants.core.gateway.UserGateway;

public class UpdateUserUseCase {

    private final UserGateway userGateway;

    public UpdateUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(User user) {
        return userGateway.update(user);
    }
}
