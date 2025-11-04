package br.com.restaurants.database.domain.repository.impl;

import br.com.restaurants.database.domain.entity.AddressEntity;
import br.com.restaurants.database.domain.repository.AddressRepository;
import br.com.restaurants.database.domain.rowMapper.AddressRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
public class AddressRepositoryImpl implements AddressRepository {
    private static final AddressRowMapper ROW_MAPPER = new AddressRowMapper();
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AddressRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public AddressEntity save(AddressEntity addressEntity) {

        String sql = """
                INSERT INTO restaurant.address
                    (street, number_address, city_address, state_address, zip_code)
                VALUES
                    (:street, :number_address, :city_address, :state_address, :zip_code)
                RETURNING address_id, address_public_id, street, number_address, city_address, state_address, zip_code, last_modified_date
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("number_address", addressEntity.getNumberAddress());
        params.addValue("street", addressEntity.getStreet());
        params.addValue("city_address", addressEntity.getCity());
        params.addValue("state_address", addressEntity.getState());
        params.addValue("zip_code", addressEntity.getZipCode());
        return jdbcTemplate.queryForObject(sql, params, ROW_MAPPER);
    }

    @Override
    public AddressEntity update(AddressEntity addressEntity) {
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
        params.addValue("number_address", addressEntity.getNumberAddress());
        params.addValue("street", addressEntity.getStreet());
        params.addValue("id", addressEntity.getId());
        params.addValue("city_address", addressEntity.getCity());
        params.addValue("state_address", addressEntity.getState());
        params.addValue("zip_code", addressEntity.getZipCode());
        return jdbcTemplate.queryForObject(sql, params, ROW_MAPPER);
    }
}
