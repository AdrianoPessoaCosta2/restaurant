package br.com.restaurants.user.adapter.controller.resquest;

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
