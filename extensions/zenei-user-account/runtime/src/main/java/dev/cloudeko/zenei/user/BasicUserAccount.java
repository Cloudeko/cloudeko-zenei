package dev.cloudeko.zenei.user;

import io.vertx.sqlclient.Row;

import java.time.LocalDateTime;
import java.util.List;

public abstract class BasicUserAccount<ID, EMAIL extends EmailAddress, PHONE extends PhoneNumber> extends UserAccount<ID> {

    protected String username;

    private List<EMAIL> emailAddresses;
    private List<PHONE> phoneNumbers;

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

    public EMAIL getPrimaryEmailAddress() {
        return emailAddresses.stream().filter(EmailAddress::isPrimaryEmail).findFirst().orElse(null);
    }

    public List<EMAIL> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(List<EMAIL> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    public PHONE getPrimaryPhoneNumber() {
        return phoneNumbers.stream().filter(PhoneNumber::isPrimaryPhoneNumber).findFirst().orElse(null);
    }

    public List<PHONE> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PHONE> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
