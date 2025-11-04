package br.com.restaurants.database.domain.rowMapper;

import br.com.restaurants.database.domain.entity.AddressEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

public class AddressRowMapper implements RowMapper<AddressEntity> {
    @Override
    public AddressEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(rs.getLong("address_id"));
        addressEntity.setPublicId((UUID) rs.getObject("address_public_id"));
        addressEntity.setStreet(rs.getString("street"));
        addressEntity.setNumberAddress(rs.getString("number_address"));
        addressEntity.setCity(rs.getString("city_address"));
        addressEntity.setState(rs.getString("state_address"));
        addressEntity.setZipCode(rs.getString("zip_code"));
        addressEntity.setLastModifiedDate(rs.getObject("last_modified_date", LocalDateTime.class));
        return addressEntity;
    }
}
