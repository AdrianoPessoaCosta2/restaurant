package br.com.restaurants.infrastructure.persistence.repository.impl;

import br.com.restaurants.infrastructure.persistence.entity.UserEntity;
import br.com.restaurants.infrastructure.persistence.rowMapper.UserRowMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @InjectMocks
    private UserRepositoryImpl repository;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Mock
    private JdbcTemplate classicJdbcTemplate;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private UserEntity inputUserEntity;

    @Test
    void shouldFindAllUsers() {
        UserEntity entity = new UserEntity();
        List<UserEntity> expectedList = List.of(entity);

        when(namedParameterJdbcTemplate.getJdbcTemplate()).thenReturn(classicJdbcTemplate);
        when(classicJdbcTemplate.query(
                contains("select"),
                any(UserRowMapper.class))
        ).thenReturn(expectedList);

        List<UserEntity> result = repository.findAll();

        assertEquals(expectedList, result);
    }

    @Test
    void shouldFindByNameSuccessfully() {
        String name = "John Doe";
        UserEntity entity = new UserEntity();

        when(namedParameterJdbcTemplate.queryForObject(
                contains("WHERE u.name_users = :name"),
                eq(Map.of("name", name)),
                any(UserRowMapper.class))
        ).thenReturn(entity);

        Optional<UserEntity> result = repository.findByName(name);

        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
    }

    @Test
    void shouldReturnEmptyWhenNameNotFound() {
        String name = "Unknown";

        when(namedParameterJdbcTemplate.queryForObject(
                anyString(),
                any(Map.class),
                any(UserRowMapper.class))
        ).thenThrow(new EmptyResultDataAccessException(1));

        Optional<UserEntity> result = repository.findByName(name);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFindByIdSuccessfully() {
        UUID id = UUID.randomUUID();
        UserEntity entity = new UserEntity();

        when(namedParameterJdbcTemplate.queryForObject(
                contains("WHERE u.user_public_id = :id"),
                eq(Map.of("id", id)),
                any(UserRowMapper.class))
        ).thenReturn(entity);

        Optional<UserEntity> result = repository.findById(id);

        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
    }

    @Test
    void shouldReturnEmptyWhenIdNotFound() {
        UUID id = UUID.randomUUID();

        when(namedParameterJdbcTemplate.queryForObject(
                anyString(),
                any(Map.class),
                any(UserRowMapper.class))
        ).thenThrow(new EmptyResultDataAccessException(1));

        Optional<UserEntity> result = repository.findById(id);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldSaveUser() {
        UUID generatedId = UUID.randomUUID();
        UserEntity savedEntity = new UserEntity();

        when(inputUserEntity.getAddressId()).thenReturn(1L);
        when(inputUserEntity.getName()).thenReturn("John");
        when(inputUserEntity.getLogin()).thenReturn("john.doe");
        when(inputUserEntity.getEmail()).thenReturn("john@email.com");
        when(inputUserEntity.getPassword()).thenReturn("pass123");
        when(inputUserEntity.getTypeUser().getCode()).thenReturn("ADMIN");

        when(namedParameterJdbcTemplate.queryForObject(
                contains("INSERT INTO"),
                any(MapSqlParameterSource.class),
                eq(UUID.class))
        ).thenReturn(generatedId);

        when(namedParameterJdbcTemplate.queryForObject(
                contains("WHERE u.user_public_id = :id"),
                eq(Map.of("id", generatedId)),
                any(UserRowMapper.class))
        ).thenReturn(savedEntity);

        UserEntity result = repository.save(inputUserEntity);

        assertEquals(savedEntity, result);

        ArgumentCaptor<MapSqlParameterSource> captor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(namedParameterJdbcTemplate).queryForObject(contains("INSERT INTO"), captor.capture(), eq(UUID.class));

        MapSqlParameterSource params = captor.getValue();
        assertEquals(1L, params.getValue("address_id"));
        assertEquals("John", params.getValue("name_users"));
        assertEquals("john.doe", params.getValue("login"));
        assertEquals("ADMIN", params.getValue("type_users"));
    }

    @Test
    void shouldUpdateUser() {
        UUID publicId = UUID.randomUUID();
        UserEntity updatedEntity = new UserEntity();

        when(inputUserEntity.getPublicId()).thenReturn(publicId);
        when(inputUserEntity.getAddressId()).thenReturn(2L);
        when(inputUserEntity.getName()).thenReturn("John Updated");
        when(inputUserEntity.getEmail()).thenReturn("john.new@email.com");
        when(inputUserEntity.getTypeUser().getCode()).thenReturn("CUSTOMER");

        when(namedParameterJdbcTemplate.update(
                contains("UPDATE"),
                any(MapSqlParameterSource.class))
        ).thenReturn(1);

        when(namedParameterJdbcTemplate.queryForObject(
                contains("WHERE u.user_public_id = :id"),
                eq(Map.of("id", publicId)),
                any(UserRowMapper.class))
        ).thenReturn(updatedEntity);

        UserEntity result = repository.update(inputUserEntity);

        assertEquals(updatedEntity, result);

        ArgumentCaptor<MapSqlParameterSource> captor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(namedParameterJdbcTemplate).update(contains("UPDATE"), captor.capture());

        MapSqlParameterSource params = captor.getValue();
        assertEquals(publicId, params.getValue("id"));
        assertEquals("John Updated", params.getValue("name_users"));
        assertEquals("CUSTOMER", params.getValue("type_users"));
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

    @Test
    void shouldUpdatePasswordReturnsTrue() {
        UUID id = UUID.randomUUID();
        String newPass = "newPass123";

        when(namedParameterJdbcTemplate.update(
                contains("UPDATE restaurant.users"),
                eq(Map.of("id", id, "password", newPass)))
        ).thenReturn(1);

        boolean result = repository.updatePassword(id, newPass);

        assertTrue(result);
    }

    @Test
    void shouldFindByUserPasswordReturnsTrue() {
        String user = "user";
        String pass = "pass";

        when(namedParameterJdbcTemplate.queryForObject(
                contains("SELECT EXISTS"),
                eq(Map.of("user", user, "password", pass)),
                eq(Boolean.class))
        ).thenReturn(true);

        boolean result = repository.findByUserPassword(user, pass);

        assertTrue(result);
    }
}