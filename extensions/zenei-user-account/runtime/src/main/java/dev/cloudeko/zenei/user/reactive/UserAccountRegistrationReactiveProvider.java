package dev.cloudeko.zenei.user.reactive;

import dev.cloudeko.zenei.user.UserAccount;
import io.smallrye.mutiny.Uni;

public interface UserAccountRegistrationReactiveProvider<ID> {

    Uni<UserAccount<ID>> createUser(UserAccount<ID> entity);

    Uni<UserAccount<ID>> updateUser(UserAccount<ID> entity);

    Uni<Boolean> deleteUser(ID id);
}
