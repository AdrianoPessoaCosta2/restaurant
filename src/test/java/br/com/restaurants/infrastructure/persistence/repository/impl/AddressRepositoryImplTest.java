package br.com.restaurants.infrastructure.persistence.repository.impl;

import br.com.restaurants.core.entities.Address;
import br.com.restaurants.infrastructure.persistence.rowMapper.AddressRowMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressRepositoryImplTest {

    @InjectMocks
    private AddressRepositoryImpl repository;

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    void shouldSaveAddress() {
        Address address = new Address();
        address.setStreet("Rua Teste");
        address.setNumberAddress("123");
        address.setCity("São Paulo");
        address.setState("SP");
        address.setZipCode("00000-000");

        Address savedAddress = new Address();

        when(jdbcTemplate.queryForObject(
                anyString(),
                any(MapSqlParameterSource.class),
                any(AddressRowMapper.class))
        ).thenReturn(savedAddress);

        Address result = repository.save(address);

        assertEquals(savedAddress, result);

        ArgumentCaptor<MapSqlParameterSource> captor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(jdbcTemplate).queryForObject(anyString(), captor.capture(), any(AddressRowMapper.class));

        MapSqlParameterSource params = captor.getValue();
        assertEquals("Rua Teste", params.getValue("street"));
        assertEquals("123", params.getValue("number_address"));
        assertEquals("São Paulo", params.getValue("city_address"));
        assertEquals("SP", params.getValue("state_address"));
        assertEquals("00000-000", params.getValue("zip_code"));
    }

    @Test
    void shouldUpdateAddress() {
        Address address = new Address();
        address.setId(1L);
        address.setStreet("Rua Atualizada");
        address.setNumberAddress("456");
        address.setCity("Rio de Janeiro");
        address.setState("RJ");
        address.setZipCode("11111-111");

        Address updatedAddress = new Address();

        when(jdbcTemplate.queryForObject(
                anyString(),
                any(MapSqlParameterSource.class),
                any(AddressRowMapper.class))
        ).thenReturn(updatedAddress);

        Address result = repository.update(address);

        assertEquals(updatedAddress, result);

        ArgumentCaptor<MapSqlParameterSource> captor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(jdbcTemplate).queryForObject(anyString(), captor.capture(), any(AddressRowMapper.class));

        MapSqlParameterSource params = captor.getValue();
        assertEquals(1L, params.getValue("id"));
        assertEquals("Rua Atualizada", params.getValue("street"));
        assertEquals("456", params.getValue("number_address"));
        assertEquals("Rio de Janeiro", params.getValue("city_address"));
        assertEquals("RJ", params.getValue("state_address"));
        assertEquals("11111-111", params.getValue("zip_code"));
    }
}