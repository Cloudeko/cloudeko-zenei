package dev.cloudeko.zenei.domain.feature;

import dev.cloudeko.zenei.domain.model.Token;
import dev.cloudeko.zenei.domain.model.token.LoginMagicLinkInput;

public interface LoginUserWithMagicLink {
    Token handle(LoginMagicLinkInput loginMagicLinkInput);
}
