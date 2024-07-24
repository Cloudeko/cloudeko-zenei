package dev.cloudeko.zenei.service;

import dev.cloudeko.zenei.dto.Token;
import dev.cloudeko.zenei.models.UserEntity;
import dev.cloudeko.zenei.repository.UserRepository;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TokenService {

    @Inject
    UserRepository userRepository;

    @Inject
    RefreshTokenService refreshTokenService;

    public Uni<Token> createToken(UserEntity user) {
        return Uni.createFrom().item(issueToken(user))
                .onItem().transformToUni(token -> refreshTokenService.createRefreshToken(user)
                        .onItem().ifNotNull().transform(refreshToken -> {
                            token.setRefreshToken(refreshToken.getToken());
                            return token;
                        }));
    }

    public Uni<Token> refreshToken(String refreshToken) {
        return refreshTokenService.swapRefreshToken(refreshToken)
                .onItem().ifNotNull().transformToUni(swappedToken -> {
                    return createToken(swappedToken.getUser());
                });
    }

    private Token issueToken(UserEntity user) {
        return issueToken(user.getEmail(), user.getName(), user.isAdmin());
    }

    private Token issueToken(String email, String name, boolean admin) {
        String accessToken = Jwt.issuer("https://zenei.cloudeko.dev/")
                .subject(email)
                .groups(admin ? "admin" : "user")
                .claim("name", name)
                .sign();

        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setTokenType("bearer");
        token.setExpiresIn(3600);

        return token;
    }
}
