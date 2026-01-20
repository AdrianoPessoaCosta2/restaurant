package br.com.restaurants.infrastructure.persistence.repository;

import br.com.restaurants.infrastructure.persistence.entity.RestaurantEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantRepository {
    List<RestaurantEntity> findAll();
    Optional<RestaurantEntity> findById(UUID id);
    RestaurantEntity save(RestaurantEntity entity);
    RestaurantEntity update(RestaurantEntity entity);
    boolean deleteById(UUID id);
}