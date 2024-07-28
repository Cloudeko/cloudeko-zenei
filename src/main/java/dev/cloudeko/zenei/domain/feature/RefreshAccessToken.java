package dev.cloudeko.zenei.domain.feature;

import dev.cloudeko.zenei.domain.model.Token;
import dev.cloudeko.zenei.domain.model.token.RefreshTokenInput;

public interface RefreshAccessToken {
    Token handle(RefreshTokenInput refreshTokenInput);
}
