package br.com.restaurants.infrastructure.persistence.rowMapper;

import br.com.restaurants.core.entities.Address;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

public class AddressRowMapper implements RowMapper<Address> {
    @Override
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
        Address address = new Address();
        address.setId(rs.getLong("address_id"));
        address.setPublicId((UUID) rs.getObject("address_public_id"));
        address.setStreet(rs.getString("street"));
        address.setNumberAddress(rs.getString("number_address"));
        address.setCity(rs.getString("city_address"));
        address.setState(rs.getString("state_address"));
        address.setZipCode(rs.getString("zip_code"));
        address.setLastModifiedDate(rs.getObject("last_modified_date", LocalDateTime.class));
        return address;
    }
}
