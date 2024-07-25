package dev.cloudeko.zenei.models;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "refresh_tokens")
@EqualsAndHashCode(callSuper = true)
@NamedQuery(name = "RefreshTokenEntity.findByValidToken", query = "SELECT s.user FROM RefreshTokenEntity s WHERE s.token = :token AND s.expiresAt > CURRENT_TIMESTAMP AND s.revoked = false")
public class RefreshTokenEntity extends PanacheEntity {

    @Column(name = "token", nullable = false)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "uid", nullable = false)
    private UserEntity user;

    @Column(name = "revoked", nullable = false)
    private boolean revoked;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
