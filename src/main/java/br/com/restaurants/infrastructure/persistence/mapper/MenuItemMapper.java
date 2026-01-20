package br.com.restaurants.infrastructure.persistence.mapper;

import br.com.restaurants.core.entities.MenuItem;
import br.com.restaurants.infrastructure.persistence.entity.MenuItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {RestaurantMapper.class})
public abstract class MenuItemMapper {

    @Autowired
    protected RestaurantMapper restaurantMapper;

    @Mapping(target = "restaurant", expression = "java(restaurantMapper.toRestaurant(entity.getRestaurant()))")
    public abstract MenuItem toDomain(MenuItemEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", expression = "java(restaurantMapper.toRestaurantEntity(domain.getRestaurant()))")
    @Mapping(target = "restaurantId", source = "restaurant.id")
    public abstract MenuItemEntity toEntity(MenuItem domain);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicId", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "restaurantId", ignore = true)
    public abstract MenuItemEntity toUpdateEntity(MenuItem domain);
}