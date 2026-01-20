package br.com.restaurants.infrastructure.gateway;

import br.com.restaurants.core.entities.Address;
import br.com.restaurants.core.entities.Restaurant;
import br.com.restaurants.infrastructure.exception.ResourceNotFoundException;
import br.com.restaurants.infrastructure.persistence.entity.RestaurantEntity;
import br.com.restaurants.infrastructure.persistence.entity.UserEntity;
import br.com.restaurants.infrastructure.persistence.mapper.RestaurantMapper;
import br.com.restaurants.infrastructure.persistence.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantGatewayImplTest {

    @InjectMocks
    private RestaurantGatewayImpl gateway;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantMapper restaurantMapper;

    @Test
    void shouldSaveRestaurant() {
        Restaurant restaurant = new Restaurant();
        RestaurantEntity entity = new RestaurantEntity();

        Address address = new Address();
        address.setId(1L);
        entity.setAddress(address);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(2L);
        entity.setUserEntity(userEntity);

        RestaurantEntity savedEntity = new RestaurantEntity();
        Restaurant expected = new Restaurant();

        when(restaurantMapper.toRestaurantEntity(restaurant)).thenReturn(entity);
        when(restaurantRepository.save(entity)).thenReturn(savedEntity);
        when(restaurantMapper.toRestaurant(savedEntity)).thenReturn(expected);

        Restaurant result = gateway.save(restaurant);

        assertEquals(expected, result);
        assertEquals(1L, entity.getAddressId());
        assertEquals(2L, entity.getOwnerId());
        verify(restaurantRepository).save(entity);
    }

    @Test
    void shouldUpdateRestaurant() {
        UUID publicId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant();
        restaurant.setPublicId(publicId);

        RestaurantEntity oldEntity = new RestaurantEntity();
        oldEntity.setId(10L);
        oldEntity.setOwnerId(5L);

        RestaurantEntity newEntity = new RestaurantEntity();
        RestaurantEntity updatedEntity = new RestaurantEntity();
        Restaurant expected = new Restaurant();

        when(restaurantRepository.findById(publicId)).thenReturn(Optional.of(oldEntity));
        when(restaurantMapper.toRestaurantUpdateEntity(restaurant)).thenReturn(newEntity);
        when(restaurantRepository.update(newEntity)).thenReturn(updatedEntity);
        when(restaurantMapper.toRestaurant(updatedEntity)).thenReturn(expected);

        Restaurant result = gateway.update(restaurant);

        assertEquals(expected, result);
        assertEquals(10L, newEntity.getId());
        assertEquals(5L, newEntity.getOwnerId());
        verify(restaurantRepository).update(newEntity);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentRestaurant() {
        UUID publicId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant();
        restaurant.setPublicId(publicId);

        when(restaurantRepository.findById(publicId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> gateway.update(restaurant));
        verify(restaurantRepository, never()).update(any());
    }

    @Test
    void shouldFindAllRestaurants() {
        RestaurantEntity entity = new RestaurantEntity();
        Restaurant restaurant = new Restaurant();

        when(restaurantRepository.findAll()).thenReturn(List.of(entity));
        when(restaurantMapper.toRestaurant(entity)).thenReturn(restaurant);

        List<Restaurant> result = gateway.findAll();

        assertEquals(1, result.size());
        assertEquals(restaurant, result.get(0));
    }

    @Test
    void shouldFindRestaurantById() {
        UUID id = UUID.randomUUID();
        RestaurantEntity entity = new RestaurantEntity();
        Restaurant restaurant = new Restaurant();

        when(restaurantRepository.findById(id)).thenReturn(Optional.of(entity));
        when(restaurantMapper.toRestaurant(entity)).thenReturn(restaurant);

        Restaurant result = gateway.findById(id);

        assertEquals(restaurant, result);
    }

    @Test
    void shouldThrowExceptionWhenFindingNonExistentId() {
        UUID id = UUID.randomUUID();
        when(restaurantRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> gateway.findById(id));
    }

    @Test
    void shouldDeleteRestaurant() {
        UUID id = UUID.randomUUID();
        when(restaurantRepository.deleteById(id)).thenReturn(true);

        gateway.deleteById(id);

        verify(restaurantRepository).deleteById(id);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentRestaurant() {
        UUID id = UUID.randomUUID();
        when(restaurantRepository.deleteById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> gateway.deleteById(id));
    }
}