package dev.cloudeko.zenei.extension.core.repository;

import dev.cloudeko.zenei.extension.core.model.account.ExternalAccessToken;

import java.util.List;

public interface ExternalAccessTokenRepository {

    List<ExternalAccessToken> listByProvider(long userId, String provider);
}
