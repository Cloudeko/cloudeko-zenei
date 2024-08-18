package dev.cloudeko.zenei.infrastructure.repository.hibernate.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "external_access_tokens")
@NamedQueries({
        @NamedQuery(name = "ExternalAccessTokenEntity.listByProvider", query = "SELECT e FROM ExternalAccessTokenEntity e WHERE e.account.user.id = :user AND e.account.provider = :provider")
})
public class ExternalAccessTokenEntity extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "external_account_id", referencedColumnName = "id", nullable = false)
    private ExternalAccountEntity account;

    @Column(name = "id_token")
    private String idToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "token_type")
    private String tokenType;

    @Column(name = "scope")
    private String scope;

    @Column(name = "access_token_expires_at")
    private Long accessTokenExpiresAt;
}
