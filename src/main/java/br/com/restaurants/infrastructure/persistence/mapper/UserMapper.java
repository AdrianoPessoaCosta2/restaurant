package br.com.restaurants.infrastructure.persistence.mapper;

import br.com.restaurants.core.entities.User;
import br.com.restaurants.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "address", source = "address")
    User toUser(UserEntity userEntity);

    @Mapping(target = "address.id", ignore = true)
    @Mapping(target = "address.publicId", ignore = true)
    @Mapping(target = "address.lastModifiedDate", ignore = true)
    @Mapping(target = "address", source = "address")
    UserEntity toUserEntity(User user);

    @Mapping(target = "address.id", ignore = true)
    @Mapping(target = "address.publicId", ignore = true)
    @Mapping(target = "address.lastModifiedDate", ignore = true)
    @Mapping(target = "address", source = "user.address")
    UserEntity toUserUpdateEntity(User user);
}