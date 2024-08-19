package dev.cloudeko.zenei.domain.mapping;

import dev.cloudeko.zenei.extension.core.model.account.ExternalAccount;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.ExternalAccountEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = QuarkusMappingConfig.class)
public interface ExternalAccountMapper {

    List<ExternalAccount> toDomainList(List<ExternalAccountEntity> entities);

    ExternalAccount toDomain(ExternalAccountEntity entity);

    void updateDomainFromEntity(ExternalAccountEntity entity, @MappingTarget ExternalAccount domain);

    ExternalAccountEntity toEntity(ExternalAccount domain);

    @AfterMapping
    default void setBackReferenceInAccessTokens(ExternalAccount account, @MappingTarget ExternalAccountEntity entity) {
        if (account.getAccessTokens() != null) {
            entity.getAccessTokens().forEach(accessToken -> accessToken.setAccount(entity));
        }
    }
}
