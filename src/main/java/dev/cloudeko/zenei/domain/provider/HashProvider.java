package dev.cloudeko.zenei.domain.provider;

public interface HashProvider {

    String hashPassword(String password);

    boolean checkPassword(String plaintext, String hashed);
}
