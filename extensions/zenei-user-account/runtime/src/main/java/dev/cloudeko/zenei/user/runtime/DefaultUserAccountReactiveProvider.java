package dev.cloudeko.zenei.user.runtime;

import dev.cloudeko.zenei.user.UserAccountReactiveProvider;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.jboss.logging.Logger;

import java.util.List;

public class DefaultUserAccountReactiveProvider implements UserAccountReactiveProvider<DefaultUserAccount, Long> {

    private static final Logger log = Logger.getLogger(DefaultUserAccountReactiveProvider.class);

    private final DefaultUserAccountRepository userAccountRepository;
    private final DefaultUserAccountEmailAddressRepository emailAddressRepository;

    public DefaultUserAccountReactiveProvider(DefaultUserAccountRepository userAccountRepository,
            DefaultUserAccountEmailAddressRepository emailAddressRepository) {
        this.userAccountRepository = userAccountRepository;
        this.emailAddressRepository = emailAddressRepository;
    }

    @Override
    public Uni<DefaultUserAccount> findUserByIdentifier(Long identifier) {
        return userAccountRepository.findUserByIdentifier(identifier);
    }

    @Override
    public Uni<DefaultUserAccount> findUserByPrimaryEmailAddress(String email) {
        return userAccountRepository.findUserByPrimaryEmailAddress(email);
    }

    @Override
    public Uni<DefaultUserAccount> findUserByPrimaryPhoneNumber(String phoneNumber) {
        return userAccountRepository.findUserByPrimaryPhoneNumber(phoneNumber);
    }

    @Override
    public Uni<DefaultUserAccount> findUserByUsername(String username) {
        return userAccountRepository.findUserByUsername(username);
    }

    @Override
    public Uni<List<DefaultUserAccount>> listUsers() {
        return userAccountRepository.listUsers();
    }

    @Override
    public Uni<List<DefaultUserAccount>> listUsers(int page, int pageSize) {
        return userAccountRepository.listUsers(page, pageSize);
    }

    @Override
    public Uni<DefaultUserAccount> createUser(DefaultUserAccount basicUserAccount) {
        Uni<DefaultUserAccount> createUser = userAccountRepository.createUser(basicUserAccount);
        Multi<DefaultEmailAddress> emailAddresses = Multi.createFrom().iterable(basicUserAccount.getEmailAddresses());

        Uni<List<DefaultEmailAddress>> createdEmailAddresses = emailAddresses
                .onItem()
                .transformToUni(emailAddress -> emailAddressRepository.createUserAccountEmailAddress(basicUserAccount.getId(),
                        emailAddress))
                .concatenate()
                .collect().asList();

        return createUser.onItem().transformToUni(user -> createdEmailAddresses.onItem().transform(emails -> {
            user.setEmailAddresses(emails);
            return user;
        }));
    }

    @Override
    public Uni<DefaultUserAccount> updateUser(DefaultUserAccount basicUserAccount) {
        Uni<DefaultUserAccount> updateUser = userAccountRepository.updateUser(basicUserAccount);
        Multi<DefaultEmailAddress> emailAddresses = Multi.createFrom().iterable(basicUserAccount.getEmailAddresses());

        Uni<List<DefaultEmailAddress>> updatedEmailAddresses = emailAddresses
                .onItem()
                .transformToUni(emailAddress -> emailAddressRepository.updateUserAccountEmailAddress(basicUserAccount.getId(),
                        emailAddress))
                .concatenate()
                .collect().asList();

        return updateUser.onItem().transformToUni(user -> updatedEmailAddresses.onItem().transform(emails -> {
            user.setEmailAddresses(emails);
            return user;
        }));
    }

    @Override
    public Uni<Boolean> deleteUser(Long identifier) {
        return userAccountRepository.deleteUser(identifier);
    }
}
