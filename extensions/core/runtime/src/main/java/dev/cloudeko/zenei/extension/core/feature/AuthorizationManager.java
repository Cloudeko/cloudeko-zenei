package dev.cloudeko.zenei.extension.core.feature;

import dev.cloudeko.zenei.extension.core.model.session.SessionToken;

public interface AuthorizationManager {

    SessionToken loginWithPassword(String identifier, String password);

    SessionToken loginWithAuthorizationCode(String provider, String code);

    SessionToken swapRefreshToken(String refreshToken);
}
