package br.com.restaurants.infrastructure.persistence.repository.impl;

import br.com.restaurants.infrastructure.persistence.entity.MenuItemEntity;
import br.com.restaurants.infrastructure.persistence.rowMapper.MenuItemRowMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuItemRepositoryImplTest {

    @InjectMocks
    private MenuItemRepositoryImpl repository;

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    void shouldFindAllByRestaurantId() {
        UUID restaurantId = UUID.randomUUID();
        MenuItemEntity entity = new MenuItemEntity();
        List<MenuItemEntity> expectedList = List.of(entity);

        when(jdbcTemplate.query(
                anyString(),
                any(Map.class),
                any(MenuItemRowMapper.class))
        ).thenReturn(expectedList);

        List<MenuItemEntity> result = repository.findAllByRestaurantId(restaurantId);

        assertEquals(expectedList, result);
        verify(jdbcTemplate).query(anyString(), eq(Map.of("resId", restaurantId)), any(MenuItemRowMapper.class));
    }

    @Test
    void shouldFindByIdSuccessfully() {
        UUID id = UUID.randomUUID();
        MenuItemEntity entity = new MenuItemEntity();

        when(jdbcTemplate.queryForObject(
                anyString(),
                any(Map.class),
                any(MenuItemRowMapper.class))
        ).thenReturn(entity);

        Optional<MenuItemEntity> result = repository.findById(id);

        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
        verify(jdbcTemplate).queryForObject(anyString(), eq(Map.of("id", id)), any(MenuItemRowMapper.class));
    }

    @Test
    void shouldReturnEmptyWhenIdNotFound() {
        UUID id = UUID.randomUUID();

        when(jdbcTemplate.queryForObject(
                anyString(),
                any(Map.class),
                any(MenuItemRowMapper.class))
        ).thenThrow(new EmptyResultDataAccessException(1));

        Optional<MenuItemEntity> result = repository.findById(id);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldSaveMenuItem() {
        UUID generatedId = UUID.randomUUID();
        MenuItemEntity inputEntity = new MenuItemEntity();
        inputEntity.setRestaurantId(1L);
        inputEntity.setName("Pizza");
        inputEntity.setDescription("Delicious");
        inputEntity.setPrice(BigDecimal.TEN);
        inputEntity.setDineInOnly(false);
        inputEntity.setPhotoPath("/img/pizza.jpg");

        MenuItemEntity savedEntity = new MenuItemEntity();

        when(jdbcTemplate.queryForObject(
                contains("INSERT INTO"),
                any(MapSqlParameterSource.class),
                eq(UUID.class))
        ).thenReturn(generatedId);

        when(jdbcTemplate.queryForObject(
                contains("SELECT"),
                any(Map.class),
                any(MenuItemRowMapper.class))
        ).thenReturn(savedEntity);

        MenuItemEntity result = repository.save(inputEntity);

        assertEquals(savedEntity, result);

        ArgumentCaptor<MapSqlParameterSource> captor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(jdbcTemplate).queryForObject(contains("INSERT INTO"), captor.capture(), eq(UUID.class));

        MapSqlParameterSource params = captor.getValue();
        assertEquals(1L, params.getValue("restaurantId"));
        assertEquals("Pizza", params.getValue("name"));
        assertEquals(BigDecimal.TEN, params.getValue("price"));
        assertEquals("/img/pizza.jpg", params.getValue("photoPath"));
    }

    @Test
    void shouldUpdateMenuItem() {
        UUID publicId = UUID.randomUUID();
        MenuItemEntity inputEntity = new MenuItemEntity();
        inputEntity.setPublicId(publicId);
        inputEntity.setName("Burger");
        inputEntity.setDescription("Juicy");
        inputEntity.setPrice(BigDecimal.ONE);
        inputEntity.setDineInOnly(true);
        inputEntity.setPhotoPath("/img/burger.jpg");

        MenuItemEntity updatedEntity = new MenuItemEntity();

        when(jdbcTemplate.update(
                contains("UPDATE"),
                any(MapSqlParameterSource.class))
        ).thenReturn(1);

        when(jdbcTemplate.queryForObject(
                contains("SELECT"),
                any(Map.class),
                any(MenuItemRowMapper.class))
        ).thenReturn(updatedEntity);

        MenuItemEntity result = repository.update(inputEntity);

        assertEquals(updatedEntity, result);

        ArgumentCaptor<MapSqlParameterSource> captor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(jdbcTemplate).update(contains("UPDATE"), captor.capture());

        MapSqlParameterSource params = captor.getValue();
        assertEquals(publicId, params.getValue("id"));
        assertEquals("Burger", params.getValue("name"));
        assertEquals(BigDecimal.ONE, params.getValue("price"));
    }

    @Test
    void shouldDeleteByIdReturnsTrue() {
        UUID id = UUID.randomUUID();

        when(jdbcTemplate.update(anyString(), any(Map.class))).thenReturn(1);

        boolean result = repository.deleteById(id);

        assertTrue(result);
        verify(jdbcTemplate).update(contains("DELETE FROM"), eq(Map.of("id", id)));
    }

    @Test
    void shouldDeleteByIdReturnsFalse() {
        UUID id = UUID.randomUUID();

        when(jdbcTemplate.update(anyString(), any(Map.class))).thenReturn(0);

        boolean result = repository.deleteById(id);

        assertFalse(result);
    }
}