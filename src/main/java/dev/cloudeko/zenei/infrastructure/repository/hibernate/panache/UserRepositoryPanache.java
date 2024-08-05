package dev.cloudeko.zenei.infrastructure.repository.hibernate.panache;

import dev.cloudeko.zenei.domain.mapping.UserMapper;
import dev.cloudeko.zenei.domain.model.user.User;
import dev.cloudeko.zenei.domain.model.user.UserRepository;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@AllArgsConstructor
public class UserRepositoryPanache extends AbstractPanacheRepository<UserEntity> implements UserRepository {

    private final UserMapper userMapper;

    @Override
    public void createUser(User user) {
        final var userEntity = userMapper.toEntity(user);
        persist(userEntity);
        userMapper.updateDomainFromEntity(userEntity, user);
    }

    @Override
    public void updateEmailVerified(String email, boolean verified) {
        getEntityManager().createNamedQuery("UserEntity.updateEmailVerified")
                .setParameter("emailVerified", verified)
                .setParameter("email", email)
                .executeUpdate();
    }

    @Override
    public boolean existsByEmail(String email) {
        return find("primaryEmailAddress", email).count() > 0;
    }

    @Override
    public boolean existsByUsername(String username) {
        return find("username", username).count() > 0;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        final var userEntity = findUserEntityById(id);
        if (userEntity == null) {
            return Optional.empty();
        }

        return Optional.of(userMapper.toDomain(userEntity));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        final var userEntity = findUserEntityByEmail(email);
        return userEntity.map(userMapper::toDomain);
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
