package br.com.restaurants.database.domain.repository;

import br.com.restaurants.database.domain.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    List<UserEntity> findAll();

    Optional<UserEntity> findByName(String name);

    Optional<UserEntity> findById(UUID id);

    Optional<String> findPasswordByLogin(String login);

    UserEntity save(UserEntity userEntity);

    UserEntity update(UserEntity userEntity);

    boolean deleteById(UUID id);
}
