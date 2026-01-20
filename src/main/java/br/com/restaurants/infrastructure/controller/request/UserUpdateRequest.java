package br.com.restaurants.infrastructure.controller.request;

import br.com.restaurants.core.enums.TypeUser;

public record UserUpdateRequest(
        String name,
        String email,
        TypeUser typeUser,
        AddressRequest address
) {
}
