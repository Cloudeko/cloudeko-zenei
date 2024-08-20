package dev.cloudeko.zenei.extension.jdbc.panache.repository;

import dev.cloudeko.zenei.extension.jdbc.panache.entity.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.persistence.NoResultException;

import java.util.Optional;

public abstract class AbstractPanacheRepositoryBase<ENTITY, ID> implements PanacheRepositoryBase<ENTITY, ID> {

    protected Optional<UserEntity> findUserEntityById(Long id) {
        return Optional.ofNullable(getEntityManager().find(UserEntity.class, id));
    }

    protected Optional<UserEntity> findUserEntityByEmail(String email) {
        try {
            return Optional.of(getEntityManager().createNamedQuery("UserEntity.findByEmail", UserEntity.class)
                    .setParameter("email", email)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
