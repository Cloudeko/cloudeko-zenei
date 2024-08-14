package dev.cloudeko.zenei.infrastructure.repository.hibernate.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "email_addresses")
@NamedQuery(name = "EmailAddressEntity.confirmEmail", query = "UPDATE EmailAddressEntity e SET e.emailVerified = true, e.emailVerificationToken = null, e.emailVerificationTokenExpiresAt = null WHERE e.emailVerificationToken = :token AND e.emailVerificationTokenExpiresAt > CURRENT_TIMESTAMP")
public class EmailAddressEntity extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified;

    @Column(name = "email_verification_token")
    private String emailVerificationToken;

    @Column(name = "email_verification_token_expires_at")
    private LocalDateTime emailVerificationTokenExpiresAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
