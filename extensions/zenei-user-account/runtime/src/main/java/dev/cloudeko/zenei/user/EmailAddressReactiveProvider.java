package dev.cloudeko.zenei.user;

import io.smallrye.mutiny.Uni;

import java.util.List;

public interface EmailAddressReactiveProvider<ENTITY extends UserAccount<ID>, ID> {

    Uni<List<EmailAddress>> listEmailAddresses(ID id);

    Uni<EmailAddress> getEmailAddress(ID id, String emailAddress);

    Uni<EmailAddress> addEmailAddress(ID id, EmailAddress emailAddress);

    Uni<EmailAddress> updateEmailAddress(ID id, EmailAddress emailAddress);

    Uni<Void> removeEmailAddress(ID id, String emailAddress);
}
