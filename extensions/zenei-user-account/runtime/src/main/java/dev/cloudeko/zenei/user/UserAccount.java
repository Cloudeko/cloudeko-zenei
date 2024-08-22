package dev.cloudeko.zenei.user;

import java.time.LocalDateTime;

public abstract class UserAccount<ID> {

    private ID id;
    private String username;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserAccount() {
    }

    public UserAccount(String username, String image) {
        this(null, username, image);
    }

    public UserAccount(ID id, String username, String image) {
        this(id, username, image, null, null);
    }

    public UserAccount(ID id, String username, String image, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public record EmailAddress(String email, boolean verified, boolean primary) {
    }

    public record PhoneNumber(String number, boolean verified, boolean primary) {
    }
}
