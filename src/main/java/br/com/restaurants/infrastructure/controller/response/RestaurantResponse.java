package br.com.restaurants.infrastructure.controller.response;

import br.com.restaurants.core.entities.Address;
import java.time.LocalTime;
import java.util.UUID;

public record RestaurantResponse(
        UUID id,
        String name,
        String cuisineType,
        LocalTime openingTime,
        LocalTime closingTime,
        Address address,
        UUID ownerId,
        String ownerName
) {

}