package dev.cloudeko.zenei.user;

import java.time.LocalDateTime;

public class PhoneNumber {

    private String phoneNumber;
    private boolean primaryPhoneNumber;
    private boolean phoneNumberVerified;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PhoneNumber() {
    }

    public PhoneNumber(String phoneNumber, boolean verified, boolean primary) {
        this.phoneNumber = phoneNumber;
        this.phoneNumberVerified = verified;
        this.primaryPhoneNumber = primary;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isPrimaryPhoneNumber() {
        return primaryPhoneNumber;
    }

    public void setPrimaryPhoneNumber(boolean primaryPhoneNumber) {
        this.primaryPhoneNumber = primaryPhoneNumber;
    }

    public boolean isPhoneNumberVerified() {
        return phoneNumberVerified;
    }

    public void setPhoneNumberVerified(boolean phoneNumberVerified) {
        this.phoneNumberVerified = phoneNumberVerified;
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
