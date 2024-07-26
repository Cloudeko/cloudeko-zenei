package dev.cloudeko.zenei.domain.provider;

import dev.cloudeko.zenei.domain.model.user.User;

public interface TokenProvider {

    String generateToken(User user);
}
