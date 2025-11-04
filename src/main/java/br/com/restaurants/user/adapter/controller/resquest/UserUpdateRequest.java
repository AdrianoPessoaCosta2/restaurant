package br.com.restaurants.user.adapter.controller.resquest;

import br.com.restaurants.core.enums.TypeUser;

public record UserUpdateRequest(
        String name,
        String email,
        TypeUser typeUser,
        AddressRequest address
) {
}
