package br.com.restaurants.infrastructure.persistence.repository;

import br.com.restaurants.infrastructure.persistence.entity.MenuItemEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuItemRepository {

    MenuItemEntity save(MenuItemEntity entity);

    MenuItemEntity update(MenuItemEntity entity);

    Optional<MenuItemEntity> findById(UUID id);

    List<MenuItemEntity> findAllByRestaurantId(UUID restaurantPublicId);

    boolean deleteById(UUID id);
}