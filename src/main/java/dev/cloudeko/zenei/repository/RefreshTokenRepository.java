package dev.cloudeko.zenei.repository;

import dev.cloudeko.zenei.models.RefreshTokenEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RefreshTokenRepository implements PanacheRepository<RefreshTokenEntity> {

    public Uni<RefreshTokenEntity> findByValidRefreshToken(String token) {
        return find("#RefreshTokenEntity.findByValidSessionToken", token).firstResult();
    }
}
