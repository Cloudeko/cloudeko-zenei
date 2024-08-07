package dev.cloudeko.zenei.infrastructure.repository.hibernate.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_passwords")
@NamedQuery(name = "UserPasswordEntity.isValidPassword", query = "SELECT u FROM UserPasswordEntity u WHERE u.user.primaryEmailAddress = ?1 AND u.passwordHash = ?2")
public class UserPasswordEntity extends PanacheEntity {

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
}
