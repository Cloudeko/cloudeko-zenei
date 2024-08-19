package dev.cloudeko.zenei.extension.core.repository;

import dev.cloudeko.zenei.extension.core.model.account.ExternalAccount;

import java.util.Optional;

public interface ExternalAccountRepository {
    Optional<ExternalAccount> findByProviderId(String providerId);
}
