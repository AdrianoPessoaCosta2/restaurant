package br.com.restaurants.infrastructure.persistence.rowMapper;

import br.com.restaurants.infrastructure.persistence.entity.MenuItemEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuItemRowMapperTest {

    @Mock
    private ResultSet resultSet;

    private final MenuItemRowMapper rowMapper = new MenuItemRowMapper();

    @Test
    void shouldMapRow() throws SQLException {
        UUID publicId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        when(resultSet.getLong("menu_items_id")).thenReturn(1L);
        when(resultSet.getLong("restaurant_id")).thenReturn(0L); // Retorna 0 para evitar instanciar/mapear o RestaurantEntity aninhado
        when(resultSet.getObject("public_id")).thenReturn(publicId);
        when(resultSet.getString("name")).thenReturn("Pizza Margherita");
        when(resultSet.getString("description")).thenReturn("Molho de tomate e queijo");
        when(resultSet.getBigDecimal("price")).thenReturn(new BigDecimal("45.00"));
        when(resultSet.getBoolean("dine_in_only")).thenReturn(true);
        when(resultSet.getString("photo_path")).thenReturn("/images/pizza.jpg");
        when(resultSet.getObject("create_date", LocalDateTime.class)).thenReturn(now);
        when(resultSet.getObject("last_updated_date", LocalDateTime.class)).thenReturn(now);

        MenuItemEntity entity = rowMapper.mapRow(resultSet, 1);

        assertEquals(1L, entity.getId());
        assertEquals(publicId, entity.getPublicId());
        assertEquals("Pizza Margherita", entity.getName());
        assertEquals("Molho de tomate e queijo", entity.getDescription());
        assertEquals(new BigDecimal("45.00"), entity.getPrice());
        assertEquals(true, entity.getDineInOnly());
        assertEquals("/images/pizza.jpg", entity.getPhotoPath());
        assertEquals(now, entity.getCreateDate());
        assertEquals(now, entity.getLastUpdatedDate());
        assertNull(entity.getRestaurant());
    }
}