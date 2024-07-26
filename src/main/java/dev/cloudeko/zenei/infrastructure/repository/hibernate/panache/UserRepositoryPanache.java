package dev.cloudeko.zenei.infrastructure.repository.hibernate.panache;

import dev.cloudeko.zenei.domain.mapping.UserMapper;
import dev.cloudeko.zenei.domain.model.user.User;
import dev.cloudeko.zenei.domain.model.user.UserRepository;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.UserEntity;
import io.quarkus.hibernate.orm.panache.Panache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@AllArgsConstructor
public class UserRepositoryPanache extends AbstractPanacheRepositoryBase<UserEntity, UUID> implements UserRepository {

    private final UserMapper userMapper;

    @Override
    public void createUser(User user) {
        final var userEntity = new UserEntity();

        userEntity.setEmail(user.getEmail());
        userEntity.setUsername(user.getUsername());
        userEntity.setEmailVerified(user.isEmailVerified());
        userEntity.setAdmin(user.isAdmin());

        persist(userEntity);
        userMapper.updateDomainFromEntity(userEntity, user);
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
        final var userEntity = findUserEntityById(id);
        if (userEntity == null) {
            return Optional.empty();
        }

        return Optional.of(userMapper.toDomain(userEntity));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        final var userEntity = findUserEntityByEmail(email);
        if (userEntity == null) {
            return Optional.empty();
        }

        return Optional.of(userMapper.toDomain(userEntity));
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        final var userEntity = find("username", username).firstResult();
        if (userEntity == null) {
            return Optional.empty();
        }

        return Optional.of(userMapper.toDomain(userEntity));
    }
}
