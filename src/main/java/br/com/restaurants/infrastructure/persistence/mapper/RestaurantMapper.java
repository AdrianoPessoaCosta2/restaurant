package br.com.restaurants.infrastructure.persistence.mapper;

import br.com.restaurants.core.entities.Restaurant;
import br.com.restaurants.infrastructure.persistence.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class RestaurantMapper {

    @Autowired
    protected UserMapper userMapper;

    @Mapping(target = "user", expression = "java(userMapper.toUser(entity.getUserEntity()))")
    @Mapping(target = "address", source = "address")
    public abstract Restaurant toRestaurant(RestaurantEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userEntity", expression = "java(userMapper.toUserEntity(restaurant.getUser()))" )
    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "address", source = "address")
    @Mapping(target = "addressId", ignore = true)
    public abstract RestaurantEntity toRestaurantEntity(Restaurant restaurant);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicId", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "userEntity", ignore = true)
    @Mapping(target = "address", source = "address")
    public abstract RestaurantEntity toRestaurantUpdateEntity(Restaurant restaurant);
}