package dev.cloudeko.zenei.infrastructure.provider;

import dev.cloudeko.zenei.domain.model.user.User;
import dev.cloudeko.zenei.domain.provider.TokenProvider;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;

@ApplicationScoped
public class JwtTokenProvider implements TokenProvider {

    private String issuer;
    private Integer expirationTimeInMinutes;

    public JwtTokenProvider(
            @ConfigProperty(name = "mp.jwt.verify.issuer") String issuer/*,
            @ConfigProperty(name = "jwt.secret") String secret,
            @ConfigProperty(name = "jwt.expiration.time.minutes") Integer expirationTimeInMinutes*/) {

        this.issuer = issuer;
        //this.expirationTimeInMinutes = expirationTimeInMinutes;
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
