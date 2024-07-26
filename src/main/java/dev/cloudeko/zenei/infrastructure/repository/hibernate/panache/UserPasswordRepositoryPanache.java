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
        UserEntity user = findUserEntityById(userPassword.getUser());
        UserPasswordEntity userPasswordEntity = new UserPasswordEntity();

        userPasswordEntity.setUser(user);
        userPasswordEntity.setPasswordHash(userPassword.getPasswordHash());

        persist(userPasswordEntity);
    }

    @Override
    public void updateUserPassword(UserPassword userPassword) {
        UserEntity user = findUserEntityById(userPassword.getUser());
        UserPasswordEntity userPasswordEntity = find("user", user).firstResult();

        userPasswordEntity.setPasswordHash(userPassword.getPasswordHash());

        persist(userPasswordEntity);
    }

    @Override
    public Optional<UserPassword> getUserPasswordByEmailAndPassword(String email, String password) {
        UserEntity user = findUserEntityByEmail(email);
        UserPasswordEntity userPasswordEntity = find("user", user).firstResult();

        if (userPasswordEntity == null) {
            return Optional.empty();
        }

        return Optional.of(new UserPassword());
    }
}
