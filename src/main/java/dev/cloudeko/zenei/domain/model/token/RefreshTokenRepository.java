package dev.cloudeko.zenei.domain.model.token;

import java.util.Optional;

public interface RefreshTokenRepository {

    RefreshToken createRefreshToken(RefreshToken refreshToken);

    Optional<RefreshToken> findRefreshTokenByToken(String token);


}
