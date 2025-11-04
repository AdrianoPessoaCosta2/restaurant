package br.com.restaurants.database.domain.rowMapper;

import br.com.restaurants.core.enums.TypeUser;
import br.com.restaurants.database.domain.entity.AddressEntity;
import br.com.restaurants.database.domain.entity.UserEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserRowMapper implements RowMapper<UserEntity> {

    private final AddressRowMapper addressRowMapper = new AddressRowMapper();

    @Override
    public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(rs.getLong("users_id"));
        userEntity.setAddressId(rs.getLong("address_id"));
        userEntity.setPublicId((UUID) rs.getObject("user_public_id"));
        userEntity.setName(rs.getString("name_users"));
        userEntity.setLogin(rs.getString("login"));
        userEntity.setEmail(rs.getString("email"));
        userEntity.setTypeUser(TypeUser.fromCode(rs.getString("type_users")));
        userEntity.setCreateDate(rs.getObject("create_date", LocalDateTime.class));
        userEntity.setLastUpdatedDate(rs.getObject("last_updated_date", LocalDateTime.class));

        if (userEntity.getAddressId() != null && userEntity.getAddressId() > 0) {
            AddressEntity addressEntity = addressRowMapper.mapRow(rs, rowNum);
            userEntity.setAddressEntity(addressEntity);
        }
        return userEntity;
    }
}