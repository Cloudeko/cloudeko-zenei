package dev.cloudeko.zenei.repository;

import dev.cloudeko.zenei.models.RefreshTokenEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SessionRepository implements PanacheRepository<RefreshTokenEntity> {

    public Uni<RefreshTokenEntity> findByValidSessionToken(String sessionToken) {
        return find("#RefreshTokenEntity.findByValidSessionToken", sessionToken).firstResult();
    }
}
