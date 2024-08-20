package dev.cloudeko.zenei.extension.jdbc.panache.repository;

import dev.cloudeko.zenei.extension.core.model.user.User;
import dev.cloudeko.zenei.extension.core.repository.UserRepository;
import dev.cloudeko.zenei.extension.jdbc.panache.entity.UserEntity;
import dev.cloudeko.zenei.extension.jdbc.panache.mapping.UserMapper;
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
        UserEntity userEntity = userMapper.toEntity(user);

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
        return findUserEntityById(id).map(userMapper::toDomain);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return findUserEntityByEmail(email).map(userMapper::toDomain);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        UserEntity user = find("username", username).firstResult();
        if (user == null) {
            return Optional.empty();
        }

        return Optional.of(user).map(userMapper::toDomain);
    }

    @Override
    public Optional<User> getByAccountProviderId(String providerId) {
        UserEntity user = find("#UserEntity.findByAccountProviderId", with("providerId", providerId)).firstResult();
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
