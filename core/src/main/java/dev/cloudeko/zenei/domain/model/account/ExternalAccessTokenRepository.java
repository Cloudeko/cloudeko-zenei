package dev.cloudeko.zenei.domain.model.account;

import java.util.List;

public interface ExternalAccessTokenRepository {

    List<ExternalAccessToken> listByProvider(long userId, String provider);
}
