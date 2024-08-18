package dev.cloudeko.zenei.domain.feature;

import dev.cloudeko.zenei.domain.model.account.ExternalAccessToken;

import java.util.List;

public interface ListExternalAccessTokens {
    List<ExternalAccessToken> listByProvider(long userId, String provider);
}
