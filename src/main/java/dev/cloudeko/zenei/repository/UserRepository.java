package dev.cloudeko.zenei.repository;

import dev.cloudeko.zenei.models.UserEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<UserEntity, UUID> {

    public Uni<UserEntity> findByEmail(String email) {
        return find("#UserEntity.findByEmail", email).firstResult();
    }

    public Uni<UserEntity> findByName(String name) {
        return find("#UserEntity.findByName", name).firstResult();
    }
}
