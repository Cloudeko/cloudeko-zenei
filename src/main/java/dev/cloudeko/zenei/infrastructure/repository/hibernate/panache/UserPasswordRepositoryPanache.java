package dev.cloudeko.zenei.infrastructure.repository.hibernate.panache;

import dev.cloudeko.zenei.domain.model.user.UserPassword;
import dev.cloudeko.zenei.domain.model.user.UserPasswordRepository;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.UserEntity;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.UserPasswordEntity;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class UserPasswordRepositoryPanache extends AbstractPanacheRepository<UserPasswordEntity>
        implements UserPasswordRepository {

    @Override
    public void createUserPassword(UserPassword userPassword) {
        if (userPassword == null || userPassword.getUser() == null) {
            throw new IllegalArgumentException("User reference cannot be null");
        }

        final var user = findUserEntityById(userPassword.getUser());
        final var userPasswordEntity = new UserPasswordEntity();

        userPasswordEntity.setUser(user);
        userPasswordEntity.setPasswordHash(userPassword.getPasswordHash());

        persist(userPasswordEntity);
    }

    @Override
    public void updateUserPassword(UserPassword userPassword) {
        final var user = findUserEntityById(userPassword.getUser());
        final var userPasswordEntity = find("user", user).firstResult();

        userPasswordEntity.setPasswordHash(userPassword.getPasswordHash());

        persist(userPasswordEntity);
    }

    @Override
    public Optional<UserPassword> getUserPasswordByEmail(String email) {
        final var userPasswordEntity = find("user.email", email).firstResult();

        if (userPasswordEntity == null) {
            return Optional.empty();
        }

        final var userPassword = new UserPassword();
        userPassword.setUser(userPasswordEntity.getUser().getId());
        userPassword.setPasswordHash(userPasswordEntity.getPasswordHash());

        return Optional.of(userPassword);
    }
}
