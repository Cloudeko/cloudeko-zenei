package dev.cloudeko.zenei.extension.jdbc.panache.repository;

import dev.cloudeko.zenei.extension.core.model.account.ExternalAccount;
import dev.cloudeko.zenei.extension.core.repository.ExternalAccountRepository;
import dev.cloudeko.zenei.extension.jdbc.panache.entity.ExternalAccountEntity;
import dev.cloudeko.zenei.extension.jdbc.panache.mapping.ExternalAccountMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.Optional;

import static io.quarkus.panache.common.Parameters.with;

@ApplicationScoped
@AllArgsConstructor
public class ExternalAccountRepositoryPanache extends AbstractPanacheRepository<ExternalAccountEntity> implements
        ExternalAccountRepository {

    private final ExternalAccountMapper externalAccountMapper;

    @Override
    public Optional<ExternalAccount> findByProviderId(String providerId) {
        ExternalAccountEntity accountEntity = find("#ExternalAccountEntity.findByProviderId", ExternalAccountEntity.class,
                with("providerId", providerId)).firstResult();

        return Optional.ofNullable(accountEntity).map(externalAccountMapper::toDomain);
    }
}
