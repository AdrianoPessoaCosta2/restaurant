package br.com.restaurants.infrastructure.controller.request;

import br.com.restaurants.core.enums.TypeUser;

public record UserCreateRequest(
        String name,
        String login,
        String email,
        String password,
        TypeUser typeUser,
        AddressRequest address
) {
}
