package br.com.restaurants.infrastructure.persistence.rowMapper;

import br.com.restaurants.infrastructure.persistence.entity.MenuItemEntity;
import br.com.restaurants.infrastructure.persistence.entity.RestaurantEntity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

public class MenuItemRowMapper implements RowMapper<MenuItemEntity> {

    private final RestaurantRowMapper restaurantRowMapper = new RestaurantRowMapper();

    @Override
    public MenuItemEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        MenuItemEntity entity = new MenuItemEntity();
        entity.setId(rs.getLong("menu_items_id"));
        entity.setRestaurantId(rs.getLong("restaurant_id"));
        entity.setPublicId((UUID) rs.getObject("public_id"));
        entity.setName(rs.getString("name"));
        entity.setDescription(rs.getString("description"));
        entity.setPrice(rs.getBigDecimal("price"));
        entity.setDineInOnly(rs.getBoolean("dine_in_only"));
        entity.setPhotoPath(rs.getString("photo_path"));
        entity.setCreateDate(rs.getObject("create_date", LocalDateTime.class));
        entity.setLastUpdatedDate(rs.getObject("last_updated_date", LocalDateTime.class));

        if (entity.getRestaurantId() != null && entity.getRestaurantId() > 0) {
            RestaurantEntity res = restaurantRowMapper.mapRow(rs, rowNum);
            entity.setRestaurant(res);
        }
        return entity;
    }
}