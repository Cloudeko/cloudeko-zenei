package dev.cloudeko.zenei.domain.mapping;

import dev.cloudeko.zenei.domain.model.user.User;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = QuarkusMappingConfig.class)
public interface UserMapper {

    List<User> toDomainList(List<UserEntity> entities);

    User toDomain(UserEntity entity);
}
