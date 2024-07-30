package dev.cloudeko.zenei.domain.mapping;

import dev.cloudeko.zenei.domain.model.mail.ConfirmationToken;
import dev.cloudeko.zenei.domain.model.user.User;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.ConfirmationTokenEntity;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = QuarkusMappingConfig.class)
public interface ConfirmationTokenMapper {

    List<ConfirmationToken> toDomainList(List<ConfirmationTokenEntity> entities);

    @Mapping(target = "user", source = "user.id")
    ConfirmationToken toDomain(ConfirmationTokenEntity entity);

    @Mapping(target = "user", source = "user.id")
    void updateDomainFromEntity(ConfirmationTokenEntity entity, @MappingTarget ConfirmationToken domain);
}
