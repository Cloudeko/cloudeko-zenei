package dev.cloudeko.zenei.user;

import io.vertx.sqlclient.Row;

import java.time.LocalDateTime;
import java.util.List;

public abstract class BasicUserAccount<ID> extends UserAccount<ID> {

    protected String username;

    private List<EmailAddress> emailAddresses;
    private List<PhoneNumber> phoneNumbers;

    public BasicUserAccount() {

    }

    public BasicUserAccount(String username) {
        this.username = username;
    }

    protected BasicUserAccount(ID id, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, createdAt, updatedAt);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public EmailAddress getPrimaryEmailAddress() {
        return emailAddresses.stream().filter(EmailAddress::primary).findFirst().orElse(null);
    }

    public List<EmailAddress> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(List<EmailAddress> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    public PhoneNumber getPrimaryPhoneNumber() {
        return phoneNumbers.stream().filter(PhoneNumber::primary).findFirst().orElse(null);
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public record EmailAddress(String email, boolean verified, boolean primary) {
    }

    public record PhoneNumber(String number, boolean verified, boolean primary) {
    }
}
