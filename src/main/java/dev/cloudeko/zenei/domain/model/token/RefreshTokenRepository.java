package dev.cloudeko.zenei.domain.model.token;

import java.util.Optional;

public interface RefreshTokenRepository {

    RefreshToken createRefreshToken(RefreshToken refreshToken);

    RefreshToken swapRefreshToken(RefreshToken oldRefreshToken, RefreshToken newRefreshToken);

    Optional<RefreshToken> findRefreshTokenByToken(String token);

}
