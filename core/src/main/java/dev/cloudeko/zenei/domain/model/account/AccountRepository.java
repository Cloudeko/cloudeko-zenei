package dev.cloudeko.zenei.domain.model.account;

import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findByProviderId(String providerId);
}
