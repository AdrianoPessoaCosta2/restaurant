package br.com.restaurants.infrastructure.persistence.rowMapper;

import br.com.restaurants.core.enums.TypeUser;
import br.com.restaurants.infrastructure.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRowMapperTest {

    @Mock
    private ResultSet resultSet;

    private final UserRowMapper rowMapper = new UserRowMapper();

    @Test
    void shouldMapRowWithAddress() throws SQLException {
        UUID publicId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        UUID addressPublicId = UUID.randomUUID();

        when(resultSet.getLong("users_id")).thenReturn(1L);
        when(resultSet.getLong("address_id")).thenReturn(10L);
        when(resultSet.getObject("user_public_id")).thenReturn(publicId);
        when(resultSet.getString("name_users")).thenReturn("John Doe");
        when(resultSet.getString("login")).thenReturn("johndoe");
        when(resultSet.getString("email")).thenReturn("john@email.com");
        when(resultSet.getString("type_users")).thenReturn("ADMIN");
        when(resultSet.getObject("create_date", LocalDateTime.class)).thenReturn(now);
        when(resultSet.getObject("last_updated_date", LocalDateTime.class)).thenReturn(now);

        // Mocks para AddressRowMapper interno
        when(resultSet.getObject("address_public_id")).thenReturn(addressPublicId);
        when(resultSet.getString("street")).thenReturn("Street Test");
        when(resultSet.getString("number_address")).thenReturn("123");
        when(resultSet.getString("city_address")).thenReturn("City");
        when(resultSet.getString("state_address")).thenReturn("ST");
        when(resultSet.getString("zip_code")).thenReturn("00000");

        try (MockedStatic<TypeUser> typeUserMock = Mockito.mockStatic(TypeUser.class)) {
            TypeUser typeUser = mock(TypeUser.class);
            typeUserMock.when(() -> TypeUser.fromCode("ADMIN")).thenReturn(typeUser);

            UserEntity entity = rowMapper.mapRow(resultSet, 1);

            assertEquals(1L, entity.getId());
            assertEquals(10L, entity.getAddressId());
            assertEquals(publicId, entity.getPublicId());
            assertEquals("John Doe", entity.getName());
            assertEquals("johndoe", entity.getLogin());
            assertEquals("john@email.com", entity.getEmail());
            assertEquals(typeUser, entity.getTypeUser());
            assertEquals(now, entity.getCreateDate());
            assertEquals(now, entity.getLastUpdatedDate());

            assertNotNull(entity.getAddress());
            assertEquals(10L, entity.getAddress().getId());
            assertEquals("Street Test", entity.getAddress().getStreet());
        }
    }

    @Test
    void shouldMapRowWithoutAddress() throws SQLException {
        UUID publicId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        when(resultSet.getLong("users_id")).thenReturn(1L);
        when(resultSet.getLong("address_id")).thenReturn(0L);
        when(resultSet.getObject("user_public_id")).thenReturn(publicId);
        when(resultSet.getString("name_users")).thenReturn("Jane Doe");
        when(resultSet.getString("type_users")).thenReturn("CUSTOMER");
        when(resultSet.getString("login")).thenReturn("login");
        when(resultSet.getString("email")).thenReturn("email");
        when(resultSet.getObject("create_date", LocalDateTime.class)).thenReturn(now);

        try (MockedStatic<TypeUser> typeUserMock = Mockito.mockStatic(TypeUser.class)) {
            TypeUser typeUser = mock(TypeUser.class);
            typeUserMock.when(() -> TypeUser.fromCode("CUSTOMER")).thenReturn(typeUser);

            UserEntity entity = rowMapper.mapRow(resultSet, 1);

            assertEquals(1L, entity.getId());
            assertEquals(0L, entity.getAddressId());
            assertNull(entity.getAddress());
        }
    }
}