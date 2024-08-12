package dev.cloudeko.zenei.domain.feature;

import dev.cloudeko.zenei.domain.model.Token;

public interface LoginUserWithAuthorizationCode {
    Token handle(String code);
}
