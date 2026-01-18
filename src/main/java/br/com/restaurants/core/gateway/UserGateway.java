package br.com.restaurants.core.gateway;

import br.com.restaurants.user.adapter.controller.response.UserResponse;
import br.com.restaurants.user.adapter.controller.resquest.UserCreateRequest;

import java.util.List;
import java.util.UUID;

public interface UserGateway {

    List<UserResponse> findAll();

    UserResponse findByName(String name);

    UserResponse findById(UUID id);

    UserResponse save(UserCreateRequest request);

    void deleteById(UUID id);
}
