package br.com.restaurants.infrastructure.persistence.repository.impl;

import br.com.restaurants.infrastructure.persistence.entity.RestaurantEntity;
import br.com.restaurants.infrastructure.persistence.repository.RestaurantRepository;
import br.com.restaurants.infrastructure.persistence.rowMapper.RestaurantRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private static final RestaurantRowMapper ROW_MAPPER = new RestaurantRowMapper();
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RestaurantRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String COMMON_SELECT = """
            SELECT 
                r.restaurants_id, r.restaurant_public_id, r.name, r.cuisine_type, 
                r.opening_time, r.closing_time, r.create_date, r.last_updated_date,
                r.address_id, r.owner_id,
                a.address_public_id, a.street, a.number_address, a.city_address, 
                a.state_address, a.zip_code, a.last_modified_date,
                u.users_id, u.user_public_id, u.name_users, u.login, u.email, u.type_users,
                u.create_date as user_create_date, u.last_updated_date as user_update_date
            FROM restaurant.restaurants r
            LEFT JOIN restaurant.address a ON a.address_id = r.address_id
            LEFT JOIN restaurant.users u ON u.users_id = r.owner_id
            """;

    @Override
    public List<RestaurantEntity> findAll() {
        return jdbcTemplate.getJdbcTemplate().query(COMMON_SELECT, ROW_MAPPER);
    }

    @Override
    public Optional<RestaurantEntity> findById(UUID id) {
        String sql = COMMON_SELECT + " WHERE r.restaurant_public_id = :id";
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(sql, Map.of("id", id), ROW_MAPPER)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public RestaurantEntity save(RestaurantEntity entity) {
        String sql = """
            INSERT INTO restaurant.restaurants
                (address_id, owner_id, name, cuisine_type, opening_time, closing_time)
            VALUES
                (:address_id, :owner_id, :name, :cuisine_type, :opening_time, :closing_time)
            RETURNING restaurant_public_id
            """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("address_id", entity.getAddressId());
        params.addValue("owner_id", entity.getUserEntity().getId());
        params.addValue("name", entity.getName());
        params.addValue("cuisine_type", entity.getCuisineType());
        params.addValue("opening_time", entity.getOpeningTime());
        params.addValue("closing_time", entity.getClosingTime());

        UUID generatedId = jdbcTemplate.queryForObject(sql, params, UUID.class);

        return findById(generatedId).orElseThrow();
    }

    @Override
    public RestaurantEntity update(RestaurantEntity entity) {
        String sql = """
            UPDATE restaurant.restaurants
            SET name = :name,
                cuisine_type = :cuisine_type,
                opening_time = :opening_time,
                closing_time = :closing_time,
                last_updated_date = NOW()
            WHERE restaurant_public_id = :id
            """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", entity.getPublicId());
        params.addValue("name", entity.getName());
        params.addValue("cuisine_type", entity.getCuisineType());
        params.addValue("opening_time", entity.getOpeningTime());
        params.addValue("closing_time", entity.getClosingTime());

        jdbcTemplate.update(sql, params);

        return findById(entity.getPublicId()).orElseThrow();
    }

    @Override
    public boolean deleteById(UUID id) {
        String sql = "DELETE FROM restaurant.restaurants WHERE restaurant_public_id = :id";
        int rows = jdbcTemplate.update(sql, Map.of("id", id));
        return rows > 0;
    }
}