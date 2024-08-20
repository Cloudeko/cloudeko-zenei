package dev.cloudeko.zenei.domain.provider;

public interface StringTokenProvider {

    default String generateToken(String token) {
        return generateToken("", token);
    }

    String generateToken(String prefix, String token);
}
