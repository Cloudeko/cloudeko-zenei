package dev.cloudeko.zenei.user;

import java.time.LocalDateTime;
import java.util.List;

public interface UserAccount<ID> {

    ID getId();

    String getUsername();

    void setUsername(String username);

    default EmailAddress getPrimaryEmailAddress() {
        return getEmailAddresses().stream().filter(EmailAddress::isPrimaryEmail).findFirst().orElse(null);
    }

    List<EmailAddress> getEmailAddresses();

    void setEmailAddresses(List<EmailAddress> emailAddresses);

    default PhoneNumber getPrimaryPhoneNumber() {
        return getPhoneNumbers().stream().filter(PhoneNumber::isPrimaryPhoneNumber).findFirst().orElse(null);
    }

    List<PhoneNumber> getPhoneNumbers();

    void setPhoneNumbers(List<PhoneNumber> phoneNumbers);

    LocalDateTime getCreatedAt();

    void setCreatedAt(LocalDateTime createdAt);

    LocalDateTime getUpdatedAt();

    void setUpdatedAt(LocalDateTime updatedAt);
}
