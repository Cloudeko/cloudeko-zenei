package dev.cloudeko.zenei.infrastructure.repository.hibernate.entity;

import dev.cloudeko.zenei.extension.core.model.session.Strategy;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "UserEntity.findByEmail", query = "SELECT u FROM UserEntity u WHERE u.primaryEmailAddress = :email"),
        @NamedQuery(name = "UserEntity.findByUsername", query = "SELECT u FROM UserEntity u WHERE u.username = :username"),
        @NamedQuery(name = "UserEntity.findByAccountProviderId", query = "SELECT u FROM UserEntity u JOIN u.accounts a WHERE a.providerId = :providerId"),
})
public class UserEntity extends PanacheEntity {

    @Column(name = "strategy")
    @Enumerated(EnumType.STRING)
    private Strategy strategy;

    @Column(name = "username", length = 64)
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "primary_email_address", unique = true)
    private String primaryEmailAddress;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmailAddressEntity> emailAddresses = new ArrayList<>();

    @Column(name = "image")
    private String image;

    @Column(name = "admin")
    private boolean admin;

    @Column(name = "password_enabled")
    private boolean passwordEnabled;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SessionEntity> sessions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExternalAccountEntity> accounts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefreshTokenEntity> refreshTokens = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
