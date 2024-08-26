package dev.cloudeko.zenei.user.reactive;

import dev.cloudeko.zenei.user.UserAccount;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.Map;

public interface UserAccountSearchReactiveProvider<ID> {

    String USERNAME_SEARCH_PARAM = "username";
    String FIRST_NAME_SEARCH_PARAM = "firstName";
    String LAST_NAME_SEARCH_PARAM = "lastName";

    Uni<Long> countUsers(Map<String, String> params);

    default Multi<UserAccount<ID>> searchForUsersByUsername(String username) {
        return searchForUsers(Map.of(USERNAME_SEARCH_PARAM, username));
    }

    Multi<UserAccount<ID>> searchForUsers(Map<String, String> params);

    Multi<UserAccount<ID>> searchForUsers(Map<String, String> params, int page, int pageSize);
}