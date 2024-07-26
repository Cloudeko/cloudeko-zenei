package dev.cloudeko.zenei.infrastructure.repository.hibernate.panache;

import dev.cloudeko.zenei.domain.model.user.User;
import dev.cloudeko.zenei.domain.model.user.UserRepository;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserRepositoryPanache extends AbstractPanacheRepositoryBase<UserEntity, UUID> implements UserRepository {

    @Override
    public User createUser(User user) {
        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(user.getEmail());
        userEntity.setUsername(user.getUsername());
        userEntity.setEmailVerified(user.isEmailVerified());
        userEntity.setAdmin(user.isAdmin());

        persist(userEntity);

        return new User();
    }

    @Override
    public boolean existsByEmail(String email) {
        return find("email", email).count() > 0;
    }

    @Override
    public boolean existsByUsername(String username) {
        return find("username", username).count() > 0;
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        UserEntity userEntity = findUserEntityById(id);
        if (userEntity == null) {
            return Optional.empty();
        }

        return Optional.of(new User());
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        UserEntity userEntity = findUserEntityByEmail(email);
        if (userEntity == null) {
            return Optional.empty();
        }

        return Optional.of(new User());
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        UserEntity userEntity = find("username", username).firstResult();
        if (userEntity == null) {
            return Optional.empty();
        }

        return Optional.of(new User());
    }
}
