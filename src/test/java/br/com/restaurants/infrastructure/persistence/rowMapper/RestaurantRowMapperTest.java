package br.com.restaurants.infrastructure.persistence.rowMapper;

import br.com.restaurants.infrastructure.persistence.entity.RestaurantEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantRowMapperTest {

    @Mock
    private ResultSet resultSet;

    private final RestaurantRowMapper rowMapper = new RestaurantRowMapper();

    @Test
    void shouldMapRow() throws SQLException {
        UUID publicId = UUID.randomUUID();
        LocalTime opening = LocalTime.of(9, 0);
        LocalTime closing = LocalTime.of(22, 0);
        LocalDateTime now = LocalDateTime.now();

        when(resultSet.getLong("restaurants_id")).thenReturn(1L);
        when(resultSet.getLong("address_id")).thenReturn(0L);
        when(resultSet.getLong("owner_id")).thenReturn(0L);
        when(resultSet.getObject("restaurant_public_id")).thenReturn(publicId);
        when(resultSet.getString("name")).thenReturn("Restaurante Teste");
        when(resultSet.getString("cuisine_type")).thenReturn("Italiana");
        when(resultSet.getObject("opening_time", LocalTime.class)).thenReturn(opening);
        when(resultSet.getObject("closing_time", LocalTime.class)).thenReturn(closing);
        when(resultSet.getObject("create_date", LocalDateTime.class)).thenReturn(now);
        when(resultSet.getObject("last_updated_date", LocalDateTime.class)).thenReturn(now);

        RestaurantEntity entity = rowMapper.mapRow(resultSet, 1);

        assertEquals(1L, entity.getId());
        assertEquals(0L, entity.getAddressId());
        assertEquals(0L, entity.getOwnerId());
        assertEquals(publicId, entity.getPublicId());
        assertEquals("Restaurante Teste", entity.getName());
        assertEquals("Italiana", entity.getCuisineType());
        assertEquals(opening, entity.getOpeningTime());
        assertEquals(closing, entity.getClosingTime());
        assertEquals(now, entity.getCreateDate());
        assertEquals(now, entity.getLastUpdatedDate());
        assertNull(entity.getAddress());
        assertNull(entity.getUserEntity());
    }
}