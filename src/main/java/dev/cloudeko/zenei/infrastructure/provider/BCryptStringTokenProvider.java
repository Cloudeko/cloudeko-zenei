package dev.cloudeko.zenei.infrastructure.provider;

import dev.cloudeko.zenei.domain.provider.StringTokenProvider;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Base64;

@ApplicationScoped
public class BCryptStringTokenProvider implements StringTokenProvider {

    @Override
    public String generateToken(String prefix, String token) {
        return prefix + Base64.getEncoder().encodeToString(BcryptUtil.bcryptHash(token).getBytes());
    }
}
