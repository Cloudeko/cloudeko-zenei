package dev.cloudeko.zenei.user;

import java.time.LocalDateTime;

public abstract class UserAccount<ID> {

    private ID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserAccount() {
    }

    public UserAccount(ID id) {
        this(id, null, null);
    }

    public UserAccount(ID id, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
