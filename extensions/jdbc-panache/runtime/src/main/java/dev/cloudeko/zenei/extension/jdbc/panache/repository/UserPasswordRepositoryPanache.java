package dev.cloudeko.zenei.extension.jdbc.panache.repository;

import dev.cloudeko.zenei.extension.core.model.user.UserPassword;
import dev.cloudeko.zenei.extension.core.repository.UserPasswordRepository;
import dev.cloudeko.zenei.extension.jdbc.panache.entity.UserEntity;
import dev.cloudeko.zenei.extension.jdbc.panache.entity.UserPasswordEntity;
import dev.cloudeko.zenei.extension.jdbc.panache.mapping.UserMapper;
import dev.cloudeko.zenei.extension.jdbc.panache.mapping.UserPasswordMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
public class UserPasswordRepositoryPanache extends AbstractPanacheRepository<UserPasswordEntity>
        implements UserPasswordRepository {

    private final UserMapper userMapper;
    private final UserPasswordMapper userPasswordMapper;

    @Override
    public void createUserPassword(UserPassword userPassword) {
        if (userPassword == null || userPassword.getUser() == null) {
            throw new IllegalArgumentException("User reference cannot be null");
        }

        Optional<UserEntity> user = findUserEntityById(userPassword.getUser().getId());
        if (user.isEmpty()) {
            return;
        }

        UserPasswordEntity userPasswordEntity = new UserPasswordEntity();

        userPasswordEntity.setUser(user.get());
        userPasswordEntity.setPasswordHash(userPassword.getPasswordHash());

        persist(userPasswordEntity);
    }

    @Override
    public void updateUserPassword(UserPassword userPassword) {
        UserPasswordEntity userPasswordEntity = find("user", userPassword.getUser().getId()).firstResult();

        userPasswordEntity.setPasswordHash(userPassword.getPasswordHash());

        persist(userPasswordEntity);
    }

    @Override
    public Optional<UserPassword> getUserPasswordByEmail(String email) {
        return find("user.primaryEmailAddress", email).firstResultOptional().map(userPasswordMapper::toDomain);
    }
}
