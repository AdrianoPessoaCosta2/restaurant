package br.com.restaurants.infrastructure.controller.request;

import br.com.restaurants.core.entities.Address;
import java.time.LocalTime;
import java.util.UUID;

public record RestaurantCreateRequest(
        String name,
        String cuisineType,
        LocalTime openingTime,
        LocalTime closingTime,
        Address address,
        UUID ownerId
) {

}