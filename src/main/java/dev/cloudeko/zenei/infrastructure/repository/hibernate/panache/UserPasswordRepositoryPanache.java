package dev.cloudeko.zenei.infrastructure.repository.hibernate.panache;

import dev.cloudeko.zenei.domain.mapping.UserMapper;
import dev.cloudeko.zenei.domain.mapping.UserPasswordMapper;
import dev.cloudeko.zenei.domain.model.user.UserPassword;
import dev.cloudeko.zenei.domain.model.user.UserPasswordRepository;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.UserPasswordEntity;
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

        final var user = userPassword.getUser();
        final var userPasswordEntity = new UserPasswordEntity();

        userPasswordEntity.setUser(findUserEntityById(user.getId()));
        userPasswordEntity.setPasswordHash(userPassword.getPasswordHash());

        persist(userPasswordEntity);
    }

    @Override
    public void updateUserPassword(UserPassword userPassword) {
        final var userPasswordEntity = find("user", userPassword.getUser().getId()).firstResult();

        userPasswordEntity.setPasswordHash(userPassword.getPasswordHash());

        persist(userPasswordEntity);
    }

    @Override
    public Optional<UserPassword> getUserPasswordByEmail(String email) {
        final var userPasswordEntity = find("user.email", email).firstResult();

        if (userPasswordEntity == null) {
            return Optional.empty();
        }

        return Optional.of(userPasswordMapper.toDomain(userPasswordEntity));
    }
}
