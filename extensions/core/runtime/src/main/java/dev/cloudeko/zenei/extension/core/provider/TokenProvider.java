package dev.cloudeko.zenei.extension.core.provider;

import dev.cloudeko.zenei.extension.core.model.user.User;

public interface TokenProvider {

    String generateToken(User user);
}
