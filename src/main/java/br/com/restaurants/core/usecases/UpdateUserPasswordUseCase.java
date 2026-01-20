package br.com.restaurants.core.usecases;

import br.com.restaurants.core.gateway.UserGateway;

import java.util.UUID;

public class UpdateUserPasswordUseCase {

    private final UserGateway userGateway;

    public UpdateUserPasswordUseCase(
            UserGateway userGateway
    ) {
        this.userGateway = userGateway;
    }

    public void execute(UUID id, String password) {
        userGateway.updatePassword(id, password);
    }
}
