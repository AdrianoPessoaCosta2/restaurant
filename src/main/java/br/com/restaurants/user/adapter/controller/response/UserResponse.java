package br.com.restaurants.user.adapter.controller.response;


import br.com.restaurants.core.enums.TypeUser;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID publicId,
        String name,
        String login,
        String email,
        TypeUser typeUser,
        LocalDateTime createDate,
        LocalDateTime lastUpdatedDate,
        AddressResponse address
) {
}
