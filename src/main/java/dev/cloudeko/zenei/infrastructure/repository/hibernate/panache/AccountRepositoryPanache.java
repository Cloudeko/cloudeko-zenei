package dev.cloudeko.zenei.infrastructure.repository.hibernate.panache;

import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.AccountEntity;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.EmailAddressEntity;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class AccountRepositoryPanache extends AbstractPanacheRepository<AccountEntity> {
}
