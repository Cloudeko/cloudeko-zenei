package dev.cloudeko.zenei.extension.jdbc.panache.mapping;

import dev.cloudeko.zenei.extension.core.model.session.SessionRefreshToken;
import dev.cloudeko.zenei.extension.jdbc.panache.entity.RefreshTokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = QuarkusMappingConfig.class, uses = UserMapper.class)
public interface RefreshTokenMapper {
    List<SessionRefreshToken> toDomainList(List<RefreshTokenEntity> entities);

    SessionRefreshToken toDomain(RefreshTokenEntity entity);

    void updateDomainFromEntity(RefreshTokenEntity entity, @MappingTarget SessionRefreshToken domain);
}
