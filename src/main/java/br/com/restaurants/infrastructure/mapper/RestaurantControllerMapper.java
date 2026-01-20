package br.com.restaurants.infrastructure.mapper;

import br.com.restaurants.core.entities.Restaurant;
import br.com.restaurants.infrastructure.controller.request.RestaurantCreateRequest;
import br.com.restaurants.infrastructure.controller.response.RestaurantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RestaurantControllerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "lastUpdatedDate", ignore = true)
    @Mapping(target = "publicId", ignore = true)
    Restaurant toDomain(RestaurantCreateRequest request);

    @Mapping(source = "publicId", target = "id")
    @Mapping(source = "user.publicId", target = "ownerId")
    @Mapping(source = "user.name", target = "ownerName")
    RestaurantResponse toResponse(Restaurant domain);
}