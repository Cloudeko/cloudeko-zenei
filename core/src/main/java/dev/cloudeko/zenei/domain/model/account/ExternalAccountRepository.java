package dev.cloudeko.zenei.domain.model.account;

import java.util.Optional;

public interface ExternalAccountRepository {
    Optional<Account> findByProviderId(String providerId);
}
