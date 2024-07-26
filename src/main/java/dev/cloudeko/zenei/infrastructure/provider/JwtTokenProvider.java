package dev.cloudeko.zenei.infrastructure.provider;

import dev.cloudeko.zenei.domain.model.user.User;
import dev.cloudeko.zenei.domain.provider.TokenProvider;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JwtTokenProvider implements TokenProvider {
    @Override
    public String generateToken(User user) {
        return Jwt.issuer("https://zenei.cloudeko.dev/")
                .subject(user.getEmail())
                .groups(user.isAdmin() ? "admin" : "user")
                .claim("username", user.getUsername())
                .sign();
    }
}
