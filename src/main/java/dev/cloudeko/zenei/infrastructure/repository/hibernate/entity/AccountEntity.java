package dev.cloudeko.zenei.infrastructure.repository.hibernate.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class AccountEntity extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity userId;

    @Column(name = "type")
    private String type;

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "access_token_expires_at")
    private Long accessTokenExpiresAt;

    @Column(name = "token_type")
    private String tokenType;

    @Column(name = "scope")
    private String scope;

    @Column(name = "id_token")
    private String idToken;

    @Column(name = "session_state")
    private String sessionState;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
