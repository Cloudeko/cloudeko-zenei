package dev.cloudeko.zenei.extension.core.provider;

public interface HashProvider {

    String hashPassword(String password);

    boolean checkPassword(String plaintext, String hashed);
}
