package br.com.restaurants.infrastructure.controller.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record AddressResponse(
        UUID publicId,
        String street,
        String numberAddress,
        String city,
        String state,
        String zipCode,
        LocalDateTime lastModifiedDate
        ) {
}
