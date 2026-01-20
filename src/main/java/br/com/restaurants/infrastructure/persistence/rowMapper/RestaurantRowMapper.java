package br.com.restaurants.infrastructure.persistence.rowMapper;

import br.com.restaurants.core.entities.Address;
import br.com.restaurants.infrastructure.persistence.entity.RestaurantEntity;
import br.com.restaurants.infrastructure.persistence.entity.UserEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class RestaurantRowMapper implements RowMapper<RestaurantEntity> {

    private final AddressRowMapper addressRowMapper = new AddressRowMapper();
    private final UserRowMapper userRowMapper = new UserRowMapper();

    @Override
    public RestaurantEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        RestaurantEntity entity = new RestaurantEntity();

        entity.setId(rs.getLong("restaurants_id"));
        entity.setAddressId(rs.getLong("address_id"));
        entity.setOwnerId(rs.getLong("owner_id"));
        entity.setPublicId((UUID) rs.getObject("restaurant_public_id"));
        entity.setName(rs.getString("name"));
        entity.setCuisineType(rs.getString("cuisine_type"));
        entity.setOpeningTime(rs.getObject("opening_time", LocalTime.class));
        entity.setClosingTime(rs.getObject("closing_time", LocalTime.class));
        entity.setCreateDate(rs.getObject("create_date", LocalDateTime.class));
        entity.setLastUpdatedDate(rs.getObject("last_updated_date", LocalDateTime.class));

        if (entity.getAddressId() != null && entity.getAddressId() > 0) {
            Address address = addressRowMapper.mapRow(rs, rowNum);
            entity.setAddress(address);
        }

        if (entity.getOwnerId() != null && entity.getOwnerId() > 0) {
            UserEntity owner = userRowMapper.mapRow(rs, rowNum);
            entity.setUserEntity(owner);
        }

        return entity;
    }
}