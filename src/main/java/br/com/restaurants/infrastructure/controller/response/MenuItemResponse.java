package br.com.restaurants.infrastructure.controller.response;

import java.math.BigDecimal;
import java.util.UUID;

public record MenuItemResponse(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        Boolean dineInOnly,
        String photoPath,
        UUID restaurantId
) {}