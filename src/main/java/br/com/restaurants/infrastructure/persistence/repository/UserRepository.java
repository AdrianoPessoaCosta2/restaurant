package br.com.restaurants.infrastructure.persistence.repository;

import br.com.restaurants.infrastructure.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    List<UserEntity> findAll();

    Optional<UserEntity> findByName(String name);

    Optional<UserEntity> findById(UUID id);

    UserEntity save(UserEntity userEntity);

    UserEntity update(UserEntity userEntity);

    boolean deleteById(UUID id);

    boolean updatePassword(UUID id, String password);

    boolean findByUserPassword(String user, String password);
}
