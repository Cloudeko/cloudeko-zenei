package dev.cloudeko.zenei.infrastructure.repository.hibernate.panache;

import dev.cloudeko.zenei.domain.mapping.AccountMapper;
import dev.cloudeko.zenei.domain.model.account.Account;
import dev.cloudeko.zenei.domain.model.account.AccountRepository;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.AccountEntity;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.Optional;

import static io.quarkus.panache.common.Parameters.with;

@ApplicationScoped
@AllArgsConstructor
public class AccountRepositoryPanache extends AbstractPanacheRepository<AccountEntity> implements AccountRepository {

    private final AccountMapper accountMapper;

    @Override
    public Optional<Account> findByProviderId(String providerId) {
        final var accountEntity = find("#AccountEntity.findByProviderId", AccountEntity.class, with("providerId", providerId))
                .firstResult();

        if (accountEntity == null) {
            return Optional.empty();
        }

        return Optional.of(accountEntity).map(accountMapper::toDomain);
    }
}
