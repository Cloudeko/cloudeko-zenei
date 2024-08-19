package dev.cloudeko.zenei.domain.provider;

import dev.cloudeko.zenei.extension.core.model.user.User;

public interface RefreshTokenProvider {

    String generateRefreshToken(User user);
}
