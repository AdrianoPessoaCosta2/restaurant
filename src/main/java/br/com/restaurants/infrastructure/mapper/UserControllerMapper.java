package br.com.restaurants.infrastructure.mapper;

import br.com.restaurants.core.entities.User;
import br.com.restaurants.infrastructure.controller.response.UserResponse;
import br.com.restaurants.infrastructure.controller.request.UserCreateRequest;
import br.com.restaurants.infrastructure.controller.request.UserUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserControllerMapper {

    List<UserResponse> toResponse(List<User> users);

    UserResponse toResponse(User user);

    User toUser(UserCreateRequest request);

    @Mapping(target = "publicId", source = "id")
    User toUser(UserUpdateRequest request, UUID id);
}
