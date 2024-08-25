package dev.cloudeko.zenei.user;

import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.Optional;

public interface UserAccountProvider<ID> {

    Optional<UserAccount<ID>> findUserByIdentifierBlocking(ID id);

    UserAccount<ID> createUserBlocking(UserAccount<ID> entity);

    UserAccount<ID> updateUserBlocking(UserAccount<ID> entity);

    boolean deleteUserBlocking(ID id);
}
