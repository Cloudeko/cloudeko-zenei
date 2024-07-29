package dev.cloudeko.zenei.infrastructure.repository.hibernate.panache;

import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.persistence.NoResultException;

import java.util.Optional;
import java.util.UUID;

public abstract class AbstractPanacheRepositoryBase<ENTITY, ID> implements PanacheRepositoryBase<ENTITY, ID> {

    protected UserEntity findUserEntityById(UUID id) {
        return getEntityManager().find(UserEntity.class, id);
    }

    protected Optional<UserEntity> findUserEntityByEmail(String email) {
        try {
            return Optional.of(
                    getEntityManager().createQuery("SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class)
                            .setParameter("email", email)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
