package dev.cloudeko.zenei.domain.mapping;

import dev.cloudeko.zenei.domain.model.account.Account;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.ExternalAccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = QuarkusMappingConfig.class)
public interface AccountMapper {

    List<Account> toDomainList(List<ExternalAccountEntity> entities);

    Account toDomain(ExternalAccountEntity entity);

    void updateDomainFromEntity(ExternalAccountEntity entity, @MappingTarget Account domain);

    ExternalAccountEntity toEntity(Account domain);
}
