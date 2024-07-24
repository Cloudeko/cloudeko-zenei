package dev.cloudeko.zenei.repository;

import dev.cloudeko.zenei.models.UserPasswordEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Objects;

@ApplicationScoped
public class UserPasswordRepository implements PanacheRepository<UserPasswordEntity> {

    public Uni<UserPasswordEntity> findByValidPassword(String email, String passwordHash) {
        return find("#UserPasswordEntity.isValidPassword", email, passwordHash).firstResult();
    }
}
