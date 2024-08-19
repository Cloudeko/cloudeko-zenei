package dev.cloudeko.zenei.domain.feature;

import dev.cloudeko.zenei.extension.core.model.session.SessionToken;

public interface LoginUserWithAuthorizationCode {
    SessionToken handle(String provider, String code);
}
