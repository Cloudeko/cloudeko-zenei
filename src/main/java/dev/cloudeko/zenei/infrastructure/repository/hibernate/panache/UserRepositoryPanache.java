package dev.cloudeko.zenei.infrastructure.repository.hibernate.panache;

import dev.cloudeko.zenei.domain.mapping.UserMapper;
import dev.cloudeko.zenei.domain.model.user.User;
import dev.cloudeko.zenei.domain.model.user.UserRepository;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

import static io.quarkus.panache.common.Parameters.with;

@ApplicationScoped
@AllArgsConstructor
public class UserRepositoryPanache extends AbstractPanacheRepository<UserEntity> implements UserRepository {

    private final UserMapper userMapper;

    @Override
    public void createUser(User user) {
        final var userEntity = userMapper.toEntity(user);

        userEntity.getEmailAddresses().forEach(emailAddressEntity -> emailAddressEntity.setUser(userEntity));
        userEntity.getAccounts().forEach(accountEntity -> accountEntity.setUser(userEntity));

        persistAndFlush(userEntity);

        userMapper.updateDomainFromEntity(userEntity, user);
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
        final var user = find("username", username).firstResult();
        if (user == null) {
            return Optional.empty();
        }

        return Optional.of(user).map(userMapper::toDomain);
    }

    @Override
    public Optional<User> getByAccountProviderId(String providerId) {
        final var user = find("#UserEntity.findByAccountProviderId", with("providerId", providerId)).firstResult();
        if (user == null) {
            return Optional.empty();
        }

        return Optional.of(user).map(userMapper::toDomain);
    }

    @Override
    public List<User> listUsers(int offset, int limit) {
        return findAll().page(offset, limit).list().stream()
                .map(userMapper::toDomain)
                .toList();
    }
}
