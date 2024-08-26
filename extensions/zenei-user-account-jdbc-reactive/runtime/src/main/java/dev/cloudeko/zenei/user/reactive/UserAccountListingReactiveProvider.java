package dev.cloudeko.zenei.user.reactive;

import dev.cloudeko.zenei.user.UserAccount;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface UserAccountListingReactiveProvider<ID> {

    Uni<Long> countUsers();

    Multi<UserAccount<ID>> listUsers();

    Multi<UserAccount<ID>> listUsers(int page, int pageSize);

    Multi<UserAccount<ID>> listUsers(int page, int pageSize, String sortField, boolean ascending);
}
