package br.com.restaurants.infrastructure.persistence.repository.impl;

import br.com.restaurants.core.entities.Address;
import br.com.restaurants.infrastructure.persistence.repository.AddressRepository;
import br.com.restaurants.infrastructure.persistence.rowMapper.AddressRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AddressRepositoryImpl implements AddressRepository {
    private static final AddressRowMapper ROW_MAPPER = new AddressRowMapper();
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AddressRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Address save(Address address) {

        String sql = """
                INSERT INTO restaurant.address
                    (street, number_address, city_address, state_address, zip_code)
                VALUES
                    (:street, :number_address, :city_address, :state_address, :zip_code)
                RETURNING address_id, address_public_id, street, number_address, city_address, state_address, zip_code, last_modified_date
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("number_address", address.getNumberAddress());
        params.addValue("street", address.getStreet());
        params.addValue("city_address", address.getCity());
        params.addValue("state_address", address.getState());
        params.addValue("zip_code", address.getZipCode());
        return jdbcTemplate.queryForObject(sql, params, ROW_MAPPER);
    }

    @Override
    public Address update(Address address) {
        String sql = """
                   UPDATE restaurant.address
                      SET street = :street,
                          number_address = :number_address,
                          city_address = :city_address,
                          state_address = :state_address,
                          zip_code = :zip_code,
                          last_modified_date = NOW()
                    WHERE address_id = :id
                RETURNING address_id, address_public_id, street, number_address, city_address, state_address, zip_code, last_modified_date
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("number_address", address.getNumberAddress());
        params.addValue("street", address.getStreet());
        params.addValue("id", address.getId());
        params.addValue("city_address", address.getCity());
        params.addValue("state_address", address.getState());
        params.addValue("zip_code", address.getZipCode());
        return jdbcTemplate.queryForObject(sql, params, ROW_MAPPER);
    }
}
