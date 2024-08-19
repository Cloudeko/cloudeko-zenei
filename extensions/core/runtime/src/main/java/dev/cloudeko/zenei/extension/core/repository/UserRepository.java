package dev.cloudeko.zenei.extension.core.repository;

import dev.cloudeko.zenei.extension.core.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void createUser(User user);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserByUsername(String username);

    Optional<User> getByAccountProviderId(String providerId);

    List<User> listUsers(int offset, int limit);
}
