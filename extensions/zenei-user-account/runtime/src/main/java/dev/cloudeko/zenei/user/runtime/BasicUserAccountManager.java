package dev.cloudeko.zenei.user.runtime;

import dev.cloudeko.zenei.user.UserAccountManager;
import dev.cloudeko.zenei.user.UserAccountRepositoryBase;
import io.smallrye.mutiny.Uni;
import org.jboss.logging.Logger;

import java.util.List;

public class BasicUserAccountManager implements UserAccountManager<BasicUserAccount, Long> {

    private static final Logger log = Logger.getLogger(BasicUserAccountManager.class);

    private final UserAccountRepositoryBase<BasicUserAccount, Long> userAccountRepository;

    public BasicUserAccountManager(UserAccountRepositoryBase<BasicUserAccount, Long> userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public Uni<BasicUserAccount> findUserByIdentifier(Long identifier) {
        return userAccountRepository.findUserByIdentifier(identifier);
    }

    @Override
    public Uni<BasicUserAccount> findUserByPrimaryEmailAddress(String email) {
        return null;
    }

    @Override
    public Uni<BasicUserAccount> findUserByPrimaryPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public Uni<BasicUserAccount> findUserByUsername(String username) {
        return userAccountRepository.findUserByUsername(username);
    }

    @Override
    public Uni<List<BasicUserAccount>> listUsers() {
        return userAccountRepository.listUsers();
    }

    @Override
    public Uni<List<BasicUserAccount>> listUsers(int page, int pageSize) {
        return userAccountRepository.listUsers(page, pageSize);
    }

    @Override
    public Uni<BasicUserAccount> createUser(BasicUserAccount basicUserAccount) {
        return userAccountRepository.createUser(basicUserAccount);
    }

    @Override
    public Uni<BasicUserAccount> updateUser(BasicUserAccount basicUserAccount) {
        return userAccountRepository.updateUser(basicUserAccount);
    }

    @Override
    public Uni<Boolean> deleteUser(BasicUserAccount basicUserAccount) {
        return userAccountRepository.deleteUser(basicUserAccount);
    }
}
