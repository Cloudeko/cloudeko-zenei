package dev.cloudeko.zenei.models;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "user_passwords")
@EqualsAndHashCode(callSuper = true)
@NamedQuery(name = "UserPasswordEntity.isValidPassword", query = "SELECT u FROM UserPasswordEntity u WHERE u.user.email = :email AND u.passwordHash = :passwordHash")
public class UserPasswordEntity extends PanacheEntity {

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "uid", nullable = false)
    private UserEntity user;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
}
