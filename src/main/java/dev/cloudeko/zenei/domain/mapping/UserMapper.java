package dev.cloudeko.zenei.domain.mapping;

import dev.cloudeko.zenei.domain.model.user.User;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = QuarkusMappingConfig.class)
public interface UserMapper {

    List<User> toDomainList(List<UserEntity> entities);

    User toDomain(UserEntity entity);

    void updateDomainFromEntity(UserEntity entity, @MappingTarget User domain);
}
