package dev.cloudeko.zenei.domain.feature.impl;

import dev.cloudeko.zenei.domain.feature.ListExternalAccessTokens;
import dev.cloudeko.zenei.extension.core.model.account.ExternalAccessToken;
import dev.cloudeko.zenei.extension.core.repository.ExternalAccessTokenRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class ListExternalAccessTokensImpl implements ListExternalAccessTokens {

    private final ExternalAccessTokenRepository externalAccessTokenRepository;

    @Override
    public List<ExternalAccessToken> listByProvider(long userId, String provider) {
        return externalAccessTokenRepository.listByProvider(userId, provider);
    }
}
