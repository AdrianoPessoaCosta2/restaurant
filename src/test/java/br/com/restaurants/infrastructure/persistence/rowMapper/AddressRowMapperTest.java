package br.com.restaurants.infrastructure.persistence.rowMapper;

import br.com.restaurants.core.entities.Address;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressRowMapperTest {

    @Mock
    private ResultSet resultSet;

    private final AddressRowMapper rowMapper = new AddressRowMapper();

    @Test
    void shouldMapRow() throws SQLException {
        UUID publicId = UUID.randomUUID();
        LocalDateTime lastModified = LocalDateTime.now();

        when(resultSet.getLong("address_id")).thenReturn(1L);
        when(resultSet.getObject("address_public_id")).thenReturn(publicId);
        when(resultSet.getString("street")).thenReturn("Rua Teste");
        when(resultSet.getString("number_address")).thenReturn("123");
        when(resultSet.getString("city_address")).thenReturn("São Paulo");
        when(resultSet.getString("state_address")).thenReturn("SP");
        when(resultSet.getString("zip_code")).thenReturn("00000-000");
        when(resultSet.getObject("last_modified_date", LocalDateTime.class)).thenReturn(lastModified);

        Address address = rowMapper.mapRow(resultSet, 1);

        assertEquals(1L, address.getId());
        assertEquals(publicId, address.getPublicId());
        assertEquals("Rua Teste", address.getStreet());
        assertEquals("123", address.getNumberAddress());
        assertEquals("São Paulo", address.getCity());
        assertEquals("SP", address.getState());
        assertEquals("00000-000", address.getZipCode());
        assertEquals(lastModified, address.getLastModifiedDate());
    }
}