package dev.cloudeko.zenei.user;

import java.util.List;
import java.util.Optional;

public interface UserAccountQueryProvider<ID> {

    Optional<UserAccount<ID>> findUserByUsernameBlocking(String username);

    List<UserAccount<ID>> listUsersBlocking();

    List<UserAccount<ID>> listUsersBlocking(int page, int pageSize);
}