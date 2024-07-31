package dev.cloudeko.zenei.infrastructure.repository.hibernate.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "sessions")
public class SessionEntity {
}
