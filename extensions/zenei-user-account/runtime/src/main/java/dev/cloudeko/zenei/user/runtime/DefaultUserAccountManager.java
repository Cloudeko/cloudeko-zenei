package dev.cloudeko.zenei.user.runtime;

import dev.cloudeko.zenei.user.UserAccountManager;
import io.smallrye.mutiny.Uni;
import org.jboss.logging.Logger;

import java.util.List;

public class DefaultUserAccountManager implements UserAccountManager<DefaultUserAccount, Long> {

    private static final Logger log = Logger.getLogger(DefaultUserAccountManager.class);

    private final DefaultUserAccountRepository userAccountRepository;

    public DefaultUserAccountManager(DefaultUserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public Uni<DefaultUserAccount> findUserByIdentifier(Long identifier) {
        return userAccountRepository.findUserByIdentifier(identifier);
    }

    @Override
    public Uni<DefaultUserAccount> findUserByPrimaryEmailAddress(String email) {
        return null;
    }

    @Override
    public Uni<DefaultUserAccount> findUserByPrimaryPhoneNumber(String phoneNumber) {
        return null;
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
        return userAccountRepository.createUser(basicUserAccount);
    }

    @Override
    public Uni<DefaultUserAccount> updateUser(DefaultUserAccount basicUserAccount) {
        return userAccountRepository.updateUser(basicUserAccount);
    }

    @Override
    public Uni<Boolean> deleteUser(Long identifier) {
        return userAccountRepository.deleteUser(identifier);
    }
}
