package dev.cloudeko.zenei.extension.core.provider;

import dev.cloudeko.zenei.extension.core.model.user.User;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;

@ApplicationScoped
public class JwtTokenProvider implements TokenProvider {

    private final String issuer;

    public JwtTokenProvider(@ConfigProperty(name = "zenei.jwt.issuer") String issuer) {
        this.issuer = issuer;
    }

    @Override
    public String generateToken(User user) {
        return Jwt.issuer(issuer)
                .upn(user.getId().toString())
                .subject(user.getId().toString())
                .groups(user.isAdmin() ? "admin" : "user")
                .claim(Claims.preferred_username, user.getUsername())
                .claim(Claims.email, user.getPrimaryEmailAddress().getEmail())
                .claim(Claims.email_verified, user.getPrimaryEmailAddress().getEmailVerified())
                .sign();
    }
}
