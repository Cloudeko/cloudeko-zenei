package dev.cloudeko.zenei.models;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "sessions")
@EqualsAndHashCode(callSuper = true)
@NamedQuery(name = "UserPasswordEntity.isValidPassword", query = "SELECT u FROM UserPasswordEntity u WHERE u.user.email = ?1 AND u.passwordHash = ?2")
public class UserPasswordEntity extends PanacheEntity {

    @OneToOne
    @JoinColumn(name = "user", referencedColumnName = "uid", nullable = false)
    private UserEntity user;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
}
