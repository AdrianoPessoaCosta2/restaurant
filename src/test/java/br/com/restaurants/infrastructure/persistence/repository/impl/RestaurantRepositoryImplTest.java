package br.com.restaurants.infrastructure.persistence.repository.impl;

import br.com.restaurants.infrastructure.persistence.entity.RestaurantEntity;
import br.com.restaurants.infrastructure.persistence.entity.UserEntity;
import br.com.restaurants.infrastructure.persistence.rowMapper.RestaurantRowMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantRepositoryImplTest {

    @InjectMocks
    private RestaurantRepositoryImpl repository;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Mock
    private JdbcTemplate classicJdbcTemplate;

    @Test
    void shouldFindAllRestaurants() {
        RestaurantEntity entity = new RestaurantEntity();
        List<RestaurantEntity> expectedList = List.of(entity);

        when(namedParameterJdbcTemplate.getJdbcTemplate()).thenReturn(classicJdbcTemplate);
        when(classicJdbcTemplate.query(anyString(), any(RestaurantRowMapper.class)))
                .thenReturn(expectedList);

        List<RestaurantEntity> result = repository.findAll();

        assertEquals(expectedList, result);
        verify(classicJdbcTemplate).query(contains("SELECT"), any(RestaurantRowMapper.class));
    }

    @Test
    void shouldFindById() {
        UUID id = UUID.randomUUID();
        RestaurantEntity entity = new RestaurantEntity();

        when(namedParameterJdbcTemplate.queryForObject(
                anyString(),
                any(Map.class),
                any(RestaurantRowMapper.class))
        ).thenReturn(entity);

        Optional<RestaurantEntity> result = repository.findById(id);

        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
        verify(namedParameterJdbcTemplate).queryForObject(
                contains("SELECT"),
                eq(Map.of("id", id)),
                any(RestaurantRowMapper.class)
        );
    }

    @Test
    void shouldReturnEmptyWhenIdNotFound() {
        UUID id = UUID.randomUUID();

        when(namedParameterJdbcTemplate.queryForObject(
                anyString(),
                any(Map.class),
                any(RestaurantRowMapper.class))
        ).thenThrow(new EmptyResultDataAccessException(1));

        Optional<RestaurantEntity> result = repository.findById(id);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldSaveRestaurant() {
        UUID generatedId = UUID.randomUUID();

        UserEntity owner = new UserEntity();
        owner.setId(10L);

        RestaurantEntity inputEntity = new RestaurantEntity();
        inputEntity.setAddressId(5L);
        inputEntity.setUserEntity(owner);
        inputEntity.setName("Pasta Place");
        inputEntity.setCuisineType("Italian");
        inputEntity.setOpeningTime(LocalTime.of(9, 0));
        inputEntity.setClosingTime(LocalTime.of(22, 0));

        RestaurantEntity savedEntity = new RestaurantEntity();

        when(namedParameterJdbcTemplate.queryForObject(
                contains("INSERT INTO"),
                any(MapSqlParameterSource.class),
                eq(UUID.class))
        ).thenReturn(generatedId);

        when(namedParameterJdbcTemplate.queryForObject(
                contains("SELECT"),
                eq(Map.of("id", generatedId)),
                any(RestaurantRowMapper.class))
        ).thenReturn(savedEntity);

        RestaurantEntity result = repository.save(inputEntity);

        assertEquals(savedEntity, result);

        ArgumentCaptor<MapSqlParameterSource> captor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(namedParameterJdbcTemplate).queryForObject(contains("INSERT INTO"), captor.capture(), eq(UUID.class));

        MapSqlParameterSource params = captor.getValue();
        assertEquals(5L, params.getValue("address_id"));
        assertEquals(10L, params.getValue("owner_id"));
        assertEquals("Pasta Place", params.getValue("name"));
        assertEquals("Italian", params.getValue("cuisine_type"));
    }

    @Test
    void shouldUpdateRestaurant() {
        UUID publicId = UUID.randomUUID();
        RestaurantEntity inputEntity = new RestaurantEntity();
        inputEntity.setPublicId(publicId);
        inputEntity.setName("New Name");
        inputEntity.setCuisineType("Fusion");
        inputEntity.setOpeningTime(LocalTime.of(10, 0));
        inputEntity.setClosingTime(LocalTime.of(23, 0));

        RestaurantEntity updatedEntity = new RestaurantEntity();

        when(namedParameterJdbcTemplate.update(
                contains("UPDATE"),
                any(MapSqlParameterSource.class))
        ).thenReturn(1);

        when(namedParameterJdbcTemplate.queryForObject(
                contains("SELECT"),
                eq(Map.of("id", publicId)),
                any(RestaurantRowMapper.class))
        ).thenReturn(updatedEntity);

        RestaurantEntity result = repository.update(inputEntity);

        assertEquals(updatedEntity, result);

        ArgumentCaptor<MapSqlParameterSource> captor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(namedParameterJdbcTemplate).update(contains("UPDATE"), captor.capture());

        MapSqlParameterSource params = captor.getValue();
        assertEquals(publicId, params.getValue("id"));
        assertEquals("New Name", params.getValue("name"));
        assertEquals("Fusion", params.getValue("cuisine_type"));
    }

    @Test
    void shouldDeleteByIdReturnsTrue() {
        UUID id = UUID.randomUUID();
        when(namedParameterJdbcTemplate.update(contains("DELETE"), eq(Map.of("id", id))))
                .thenReturn(1);

        boolean result = repository.deleteById(id);

        assertTrue(result);
    }

    @Test
    void shouldDeleteByIdReturnsFalse() {
        UUID id = UUID.randomUUID();
        when(namedParameterJdbcTemplate.update(contains("DELETE"), eq(Map.of("id", id))))
                .thenReturn(0);

        boolean result = repository.deleteById(id);

        assertFalse(result);
    }
}