package br.com.restaurants.infrastructure.controller.request;

import java.math.BigDecimal;
import java.util.UUID;

public record MenuItemRequest(
        String name,
        String description,
        BigDecimal price,
        Boolean dineInOnly,
        String photoPath,
        UUID restaurantId
) {}