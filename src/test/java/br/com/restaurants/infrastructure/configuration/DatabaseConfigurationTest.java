package br.com.restaurants.infrastructure.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.util.ReflectionTestUtils;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class DatabaseConfigurationTest {

    @Test
    void shouldCreateDataSource() {
        DatabaseConfiguration config = new DatabaseConfiguration();
        ReflectionTestUtils.setField(config, "url", "jdbc:postgresql://localhost:5432/db");
        ReflectionTestUtils.setField(config, "username", "user");
        ReflectionTestUtils.setField(config, "password", "pass");

        DataSource dataSource = config.dataSource();

        assertNotNull(dataSource);
        DriverManagerDataSource driverSource = (DriverManagerDataSource) dataSource;
        assertEquals("jdbc:postgresql://localhost:5432/db", driverSource.getUrl());
        assertEquals("user", driverSource.getUsername());
        assertEquals("pass", driverSource.getPassword());
    }

    @Test
    void shouldCreateNamedParameterJdbcTemplate() {
        DatabaseConfiguration config = new DatabaseConfiguration();
        DataSource dataSource = mock(DataSource.class);

        NamedParameterJdbcTemplate template = config.namedParameterJdbcTemplate(dataSource);

        assertNotNull(template);
    }
}