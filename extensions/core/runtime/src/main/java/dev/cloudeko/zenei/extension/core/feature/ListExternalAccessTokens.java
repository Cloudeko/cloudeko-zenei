package dev.cloudeko.zenei.extension.core.feature;

import dev.cloudeko.zenei.extension.core.model.account.ExternalAccessToken;

import java.util.List;

public interface ListExternalAccessTokens {
    List<ExternalAccessToken> listByProvider(long userId, String provider);
}
