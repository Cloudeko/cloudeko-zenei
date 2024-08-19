package dev.cloudeko.zenei.extension.core.repository;

import dev.cloudeko.zenei.extension.core.model.user.UserPassword;

import java.util.Optional;

public interface UserPasswordRepository {

    void createUserPassword(UserPassword userPassword);

    void updateUserPassword(UserPassword userPassword);

    Optional<UserPassword> getUserPasswordByEmail(String email);
}
