package dev.cloudeko.zenei.domain.model.user;

import java.util.Optional;

public interface UserPasswordRepository {

    void createUserPassword(UserPassword userPassword);

    void updateUserPassword(UserPassword userPassword);

    Optional<UserPassword> getUserPasswordByEmailAndPassword(String email, String password);
}
