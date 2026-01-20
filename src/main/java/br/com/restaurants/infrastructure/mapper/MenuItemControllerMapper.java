package br.com.restaurants.infrastructure.mapper;

import br.com.restaurants.core.entities.MenuItem;
import br.com.restaurants.infrastructure.controller.request.MenuItemRequest;
import br.com.restaurants.infrastructure.controller.response.MenuItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MenuItemControllerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicId", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    MenuItem toDomain(MenuItemRequest request);

    @Mapping(target = "id", source = "publicId")
    @Mapping(target = "restaurantId", source = "restaurant.publicId")
    MenuItemResponse toResponse(MenuItem domain);
}