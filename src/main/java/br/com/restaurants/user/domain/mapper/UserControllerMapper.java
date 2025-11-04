package br.com.restaurants.user.domain.mapper;

import br.com.restaurants.core.model.User;
import br.com.restaurants.user.adapter.controller.response.UserResponse;
import br.com.restaurants.user.adapter.controller.resquest.UserCreateRequest;
import br.com.restaurants.user.adapter.controller.resquest.UserUpdateRequest;
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
