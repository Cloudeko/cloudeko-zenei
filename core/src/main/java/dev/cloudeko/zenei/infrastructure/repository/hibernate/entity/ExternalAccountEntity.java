package dev.cloudeko.zenei.infrastructure.repository.hibernate.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "external_accounts")
@NamedQueries({
        @NamedQuery(name = "AccountEntity.findByUserId", query = "SELECT a FROM ExternalAccountEntity a WHERE a.user = :userId"),
        @NamedQuery(name = "AccountEntity.findByProviderId", query = "SELECT a FROM ExternalAccountEntity a WHERE a.providerId = :providerId"),
})
public class ExternalAccountEntity extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @Column(name = "type")
    private String type;

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_id")
    private String providerId;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExternalAccessTokenEntity> accessTokens;

    @Column(name = "session_state")
    private String sessionState;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
