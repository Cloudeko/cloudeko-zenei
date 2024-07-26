package dev.cloudeko.zenei.infrastructure.repository.hibernate.panache;

import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import java.util.UUID;

public abstract class AbstractPanacheRepositoryBase<ENTITY, ID> implements PanacheRepositoryBase<ENTITY, ID> {

    protected UserEntity findUserEntityById(UUID id) {
        return getEntityManager().find(UserEntity.class, id);
    }

    protected UserEntity findUserEntityByEmail(String email) {
        return getEntityManager().createQuery("SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}
