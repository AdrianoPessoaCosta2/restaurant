package br.com.restaurants.infrastructure.gateway;

import br.com.restaurants.core.entities.MenuItem;
import br.com.restaurants.core.entities.Restaurant;
import br.com.restaurants.infrastructure.persistence.entity.MenuItemEntity;
import br.com.restaurants.infrastructure.persistence.entity.RestaurantEntity;
import br.com.restaurants.infrastructure.persistence.mapper.MenuItemMapper;
import br.com.restaurants.infrastructure.persistence.repository.impl.MenuItemRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuItemGatewayImplTest {

    @Mock
    private MenuItemRepositoryImpl repository;
    @Mock
    private MenuItemMapper mapper;
    @InjectMocks
    private MenuItemGatewayImpl gateway;

    @Test
    void shouldSaveMenuItem() {
        MenuItem domain = new MenuItem();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(10L);
        domain.setRestaurant(restaurant);

        MenuItemEntity entity = new MenuItemEntity();
        RestaurantEntity resEntity = new RestaurantEntity();
        resEntity.setId(10L);
        entity.setRestaurant(resEntity);

        MenuItemEntity savedEntity = new MenuItemEntity();
        MenuItem savedDomain = new MenuItem();

        when(mapper.toEntity(domain)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(savedEntity);
        when(mapper.toDomain(savedEntity)).thenReturn(savedDomain);

        MenuItem result = gateway.save(domain);

        assertEquals(savedDomain, result);
        assertEquals(10L, entity.getRestaurantId());
        verify(repository).save(entity);
    }

    @Test
    void shouldFindById() {
        UUID id = UUID.randomUUID();
        MenuItemEntity entity = new MenuItemEntity();
        MenuItem domain = new MenuItem();

        when(repository.findById(id)).thenReturn(java.util.Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        MenuItem result = gateway.findById(id);

        assertEquals(domain, result);
        verify(repository).findById(id);
    }
}