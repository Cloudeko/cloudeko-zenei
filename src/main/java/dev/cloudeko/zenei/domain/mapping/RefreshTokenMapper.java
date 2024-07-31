package dev.cloudeko.zenei.domain.mapping;

import dev.cloudeko.zenei.domain.model.token.RefreshToken;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.RefreshTokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = QuarkusMappingConfig.class, uses = UserMapper.class)
public interface RefreshTokenMapper {
    List<RefreshToken> toDomainList(List<RefreshTokenEntity> entities);

    RefreshToken toDomain(RefreshTokenEntity entity);

    void updateDomainFromEntity(RefreshTokenEntity entity, @MappingTarget RefreshToken domain);
}
