package dev.cloudeko.zenei.domain.feature;

import dev.cloudeko.zenei.domain.model.Token;
import dev.cloudeko.zenei.domain.model.token.LoginPasswordInput;

public interface LoginUserWithPassword {
    Token handle(LoginPasswordInput loginPasswordInput);
}
