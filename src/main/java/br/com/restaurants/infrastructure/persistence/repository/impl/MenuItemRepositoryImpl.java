package br.com.restaurants.infrastructure.persistence.repository.impl;

import br.com.restaurants.infrastructure.persistence.entity.MenuItemEntity;
import br.com.restaurants.infrastructure.persistence.repository.MenuItemRepository;
import br.com.restaurants.infrastructure.persistence.rowMapper.MenuItemRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MenuItemRepositoryImpl implements MenuItemRepository {

    private static final MenuItemRowMapper ROW_MAPPER = new MenuItemRowMapper();
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MenuItemRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String COMMON_SELECT = """
            SELECT 
                mi.menu_items_id, mi.public_id, mi.name, mi.description, mi.price, 
                mi.dine_in_only, mi.photo_path, mi.create_date, mi.last_updated_date,
                mi.restaurant_id,
                r.restaurants_id, r.restaurant_public_id, r.name as restaurant_name, r.cuisine_type, 
                r.opening_time, r.closing_time, r.address_id, r.owner_id,
                a.address_public_id, a.street, a.number_address, a.city_address, 
                a.state_address, a.zip_code, a.last_modified_date,
                u.users_id, u.user_public_id, u.name_users, u.login, u.email, u.type_users,
                u.create_date as user_create_date, u.last_updated_date as user_update_date
            FROM restaurant.menu_items mi
            INNER JOIN restaurant.restaurants r ON r.restaurants_id = mi.restaurant_id
            LEFT JOIN restaurant.address a ON a.address_id = r.address_id
            LEFT JOIN restaurant.users u ON u.users_id = r.owner_id
            """;

    @Override
    public List<MenuItemEntity> findAllByRestaurantId(UUID restaurantPublicId) {
        String sql = COMMON_SELECT + " WHERE r.restaurant_public_id = :resId";
        return jdbcTemplate.query(sql, Map.of("resId", restaurantPublicId), ROW_MAPPER);
    }

    @Override
    public Optional<MenuItemEntity> findById(UUID id) {
        String sql = COMMON_SELECT + " WHERE mi.public_id = :id";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, Map.of("id", id), ROW_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public MenuItemEntity save(MenuItemEntity entity) {
        String sql = """
            INSERT INTO restaurant.menu_items
                (restaurant_id, name, description, price, dine_in_only, photo_path)
            VALUES
                (:restaurantId, :name, :description, :price, :dineInOnly, :photoPath)
            RETURNING public_id
            """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("restaurantId", entity.getRestaurantId())
                .addValue("name", entity.getName())
                .addValue("description", entity.getDescription())
                .addValue("price", entity.getPrice())
                .addValue("dineInOnly", entity.getDineInOnly())
                .addValue("photoPath", entity.getPhotoPath());

        UUID generatedId = jdbcTemplate.queryForObject(sql, params, UUID.class);
        return findById(generatedId).orElseThrow();
    }

    @Override
    public MenuItemEntity update(MenuItemEntity entity) {
        String sql = """
            UPDATE restaurant.menu_items
            SET name = :name, description = :description, price = :price, 
                dine_in_only = :dineInOnly, photo_path = :photoPath, last_updated_date = NOW()
            WHERE public_id = :id
            """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", entity.getPublicId())
                .addValue("name", entity.getName())
                .addValue("description", entity.getDescription())
                .addValue("price", entity.getPrice())
                .addValue("dineInOnly", entity.getDineInOnly())
                .addValue("photoPath", entity.getPhotoPath());

        jdbcTemplate.update(sql, params);
        return findById(entity.getPublicId()).orElseThrow();
    }

    @Override
    public boolean deleteById(UUID id) {
        String sql = "DELETE FROM restaurant.menu_items WHERE public_id = :id";
        return jdbcTemplate.update(sql, Map.of("id", id)) > 0;
    }
}