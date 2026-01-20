package br.com.restaurants.infrastructure.gateway;

import br.com.restaurants.core.entities.Restaurant;
import br.com.restaurants.core.gateway.RestaurantGateway;
import br.com.restaurants.infrastructure.exception.ResourceNotFoundException;
import br.com.restaurants.infrastructure.persistence.entity.RestaurantEntity;
import br.com.restaurants.infrastructure.persistence.mapper.RestaurantMapper;
import br.com.restaurants.infrastructure.persistence.repository.RestaurantRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class RestaurantGatewayImpl implements RestaurantGateway {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public RestaurantGatewayImpl(
            RestaurantRepository restaurantRepository,
            RestaurantMapper restaurantMapper
    ) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        RestaurantEntity entity = restaurantMapper.toRestaurantEntity(restaurant);

        if (entity.getAddress() != null) {
            entity.setAddressId(entity.getAddress().getId());
        }
        if (entity.getUserEntity() != null) {
            entity.setOwnerId(entity.getUserEntity().getId());
        }

        return restaurantMapper.toRestaurant(restaurantRepository.save(entity));
    }

    @Override
    public Restaurant update(Restaurant restaurant) {

        RestaurantEntity oldEntity = restaurantRepository.findById(restaurant.getPublicId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        RestaurantEntity entity = restaurantMapper.toRestaurantUpdateEntity(restaurant);

        entity.setId(oldEntity.getId());

        if (entity.getAddress() != null) {
            entity.setAddressId(entity.getAddress().getId());
        }
        if (entity.getUserEntity() != null) {
            entity.setOwnerId(entity.getUserEntity().getId());
        } else {
            entity.setOwnerId(oldEntity.getOwnerId());
        }

        return restaurantMapper.toRestaurant(restaurantRepository.update(entity));
    }

    @Override
    public List<Restaurant> findAll() {
        return restaurantRepository.findAll()
                .stream()
                .map(restaurantMapper::toRestaurant)
                .toList();
    }

    @Override
    public Restaurant findById(UUID id) {
        return restaurantRepository.findById(id)
                .map(restaurantMapper::toRestaurant)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
    }

    @Override
    public void deleteById(UUID id) {
        if (!restaurantRepository.deleteById(id)) {
            throw new ResourceNotFoundException("Restaurant not found for deletion");
        }
    }
}