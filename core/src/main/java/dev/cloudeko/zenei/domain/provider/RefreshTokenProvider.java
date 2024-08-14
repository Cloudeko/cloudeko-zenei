package dev.cloudeko.zenei.domain.provider;

import dev.cloudeko.zenei.domain.model.user.User;

public interface RefreshTokenProvider {

    String generateRefreshToken(User user);
}
