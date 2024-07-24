package dev.cloudeko.zenei.mapping;

import dev.cloudeko.zenei.dto.User;
import dev.cloudeko.zenei.models.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = QuarkusMappingConfig.class)
public interface UserMapper {

    List<User> toDomainList(List<UserEntity> entities);

    User toDomain(UserEntity entity);
}
