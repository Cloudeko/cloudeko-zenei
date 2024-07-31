package dev.cloudeko.zenei.domain.mapping;

import dev.cloudeko.zenei.domain.model.email.ConfirmationToken;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.ConfirmationTokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = QuarkusMappingConfig.class, uses = UserMapper.class)
public interface ConfirmationTokenMapper {

    List<ConfirmationToken> toDomainList(List<ConfirmationTokenEntity> entities);

    ConfirmationToken toDomain(ConfirmationTokenEntity entity);

    void updateDomainFromEntity(ConfirmationTokenEntity entity, @MappingTarget ConfirmationToken domain);
}
