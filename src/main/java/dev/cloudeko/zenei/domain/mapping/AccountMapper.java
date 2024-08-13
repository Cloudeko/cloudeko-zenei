package dev.cloudeko.zenei.domain.mapping;

import dev.cloudeko.zenei.domain.model.account.Account;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = QuarkusMappingConfig.class)
public interface AccountMapper {

    List<Account> toDomainList(List<AccountEntity> entities);

    Account toDomain(AccountEntity entity);

    void updateDomainFromEntity(AccountEntity entity, @MappingTarget Account domain);

    AccountEntity toEntity(Account domain);
}
