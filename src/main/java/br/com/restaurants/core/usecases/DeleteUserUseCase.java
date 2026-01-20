package br.com.restaurants.core.usecases;

import br.com.restaurants.core.gateway.UserGateway;

import java.util.UUID;

public class DeleteUserUseCase {

    private final UserGateway userGateway;

    public DeleteUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public void execute(UUID id) {
        userGateway.deleteById(id);
    }
}
