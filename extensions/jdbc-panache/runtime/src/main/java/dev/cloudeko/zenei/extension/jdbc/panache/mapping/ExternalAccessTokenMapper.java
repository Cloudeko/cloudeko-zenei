package dev.cloudeko.zenei.extension.jdbc.panache.mapping;

import dev.cloudeko.zenei.extension.core.model.account.ExternalAccessToken;
import dev.cloudeko.zenei.extension.jdbc.panache.entity.ExternalAccessTokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = QuarkusMappingConfig.class)
public interface ExternalAccessTokenMapper {
    List<ExternalAccessToken> toDomainList(List<ExternalAccessTokenEntity> entities);

    ExternalAccessToken toDomain(ExternalAccessTokenEntity entity);

    void updateDomainFromEntity(ExternalAccessTokenEntity entity, @MappingTarget ExternalAccessToken domain);

    ExternalAccessTokenEntity toEntity(ExternalAccessToken domain);
}
