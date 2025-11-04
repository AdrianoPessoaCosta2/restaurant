package br.com.restaurants.database.domain.repository.impl;

import br.com.restaurants.database.domain.entity.UserEntity;
import br.com.restaurants.database.domain.repository.UserRepository;
import br.com.restaurants.database.domain.rowMapper.UserRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final UserRowMapper ROW_MAPPER = new UserRowMapper();
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static String COMMON_USER =
            """
                    select
                          u.users_id,
                          u.name_users,
                          u.user_public_id,
                          u.login,
                          u.email,
                          u.type_users,
                          u.create_date,
                          u.last_updated_date,
                          a.address_id,
                          a.address_public_id,
                          a.street,
                          a.number_address,
                          a.city_address,
                          a.state_address,
                          a.zip_code,
                          a.last_modified_date
                    from restaurant.users u
                    left join restaurant.address a on a.address_id = u.address_id
                    """;

    @Override
    public List<UserEntity> findAll() {
        return jdbcTemplate.getJdbcTemplate().query(COMMON_USER, ROW_MAPPER);
    }

    @Override
    public Optional<UserEntity> findByName(String name) {
        String sql = COMMON_USER + " WHERE u.name_users = :name";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, Map.of("name", name), ROW_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    @Override
    public Optional<UserEntity> findById(UUID id) {
        String sql = COMMON_USER + " WHERE u.user_public_id = :id";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, Map.of("id", id), ROW_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> findPasswordByLogin(String login) {
        String sql = """
                SELECT  password
                  FROM restaurant.users
                 WHERE login = :login
                """;
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, Map.of("login", login), String.class));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        String sql = """
                INSERT INTO restaurant.users
                    (address_id, name_users, login, email, password, type_users)
                VALUES
                    (:address_id, :name_users, :login, :email, :password, :type_users)
                RETURNING user_public_id
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("address_id", userEntity.getAddressId());
        params.addValue("name_users", userEntity.getName());
        params.addValue("login", userEntity.getLogin());
        params.addValue("email", userEntity.getEmail());
        params.addValue("password", userEntity.getPassword());
        params.addValue("type_users", userEntity.getTypeUser().getCode());
        UUID id = jdbcTemplate.queryForObject(sql, params, UUID.class);

        return findById(id).orElseThrow();
    }

    @Override
    public UserEntity update(UserEntity userEntity) {
        String sql = """
                   UPDATE restaurant.users
                      SET name_users  = :name_users,
                          email       = :email,
                          type_users  = :type_users,
                          last_updated_date = NOW()
                    WHERE user_public_id = :id
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", userEntity.getPublicId());
        params.addValue("address_id", userEntity.getAddressId());
        params.addValue("name_users", userEntity.getName());
        params.addValue("email", userEntity.getEmail());
        params.addValue("type_users", userEntity.getTypeUser().getCode());
        jdbcTemplate.update(sql, params);
        return findById(userEntity.getPublicId()).orElseThrow();
    }

    @Override
    public boolean deleteById(UUID id) {
        String sql = "DELETE FROM restaurant.users WHERE user_public_id = :id";
        int rows = jdbcTemplate.update(sql, Map.of("id", id));
        return rows > 0;
    }

    @Override
    public boolean  updatePassword(UUID id, String password) {
        String sql = """
                   UPDATE restaurant.users
                      SET password = :password
                    WHERE user_public_id = :id
                """;
        int rows = jdbcTemplate.update(sql, Map.of("id", id, "password", password));
        return rows > 0;
    }
}
