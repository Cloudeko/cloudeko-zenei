package dev.cloudeko.zenei.user;

import java.time.LocalDateTime;

public class EmailAddress {

    private String email;
    private boolean primaryEmail;
    private boolean emailVerified;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EmailAddress() {
    }

    public EmailAddress(String email, boolean verified, boolean primary) {
        this.email = email;
        this.emailVerified = verified;
        this.primaryEmail = primary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(boolean primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
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
