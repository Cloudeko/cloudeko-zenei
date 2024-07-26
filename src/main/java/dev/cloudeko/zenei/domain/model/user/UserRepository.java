package dev.cloudeko.zenei.domain.model.user;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    void createUser(User user);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> getUserById(UUID id);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserByUsername(String username);
}
