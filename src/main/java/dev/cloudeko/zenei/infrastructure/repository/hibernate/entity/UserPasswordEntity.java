package dev.cloudeko.zenei.infrastructure.repository.hibernate.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "user_passwords")
@NamedQuery(name = "UserPasswordEntity.isValidPassword", query = "SELECT u FROM UserPasswordEntity u WHERE u.user.email = ?1 AND u.passwordHash = ?2")
public class UserPasswordEntity {

    @Id
    private UUID id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "uid", nullable = false)
    private UserEntity user;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
}
