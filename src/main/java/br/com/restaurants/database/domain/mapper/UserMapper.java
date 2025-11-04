package br.com.restaurants.database.domain.mapper;

import br.com.restaurants.core.model.User;
import br.com.restaurants.database.domain.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "address", source = "addressEntity")
    User toUser(UserEntity userEntity);

    @Mapping(target = "addressEntity.id", ignore = true)
    @Mapping(target = "addressEntity.publicId", ignore = true)
    @Mapping(target = "addressEntity.lastModifiedDate", ignore = true)
    @Mapping(target = "addressEntity", source = "address")
    UserEntity toUserEntity(User user);

    @Mapping(target = "addressEntity.id", ignore = true)
    @Mapping(target = "addressEntity.publicId", ignore = true)
    @Mapping(target = "addressEntity.lastModifiedDate", ignore = true)
    @Mapping(target = "addressEntity", source = "user.address")
    UserEntity toUserUpdateEntity(User user);
}