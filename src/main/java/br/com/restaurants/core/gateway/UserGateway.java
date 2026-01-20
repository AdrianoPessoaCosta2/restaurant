package br.com.restaurants.core.gateway;

import br.com.restaurants.core.entities.User;

import java.util.List;
import java.util.UUID;

public interface UserGateway {

    List<User> findAll();
    User findById(UUID id);
    User findByName(String name);
    User save(User user);
    User update(User user);
    void deleteById(UUID id);
    void updatePassword(UUID id, String password);
    boolean validateLogin(String user, String password);
}
