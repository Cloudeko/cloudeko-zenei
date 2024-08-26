package dev.cloudeko.zenei.user;

import java.util.List;
import java.util.Map;

public interface UserAccountSearchProvider<ID> {

    String USERNAME_SEARCH_PARAM = "username";
    String FIRST_NAME_SEARCH_PARAM = "firstName";
    String LAST_NAME_SEARCH_PARAM = "lastName";

    long countUsers(Map<String, String> params);

    default List<UserAccount<ID>> searchForUsersByUsername(String username) {
        return searchForUsers(Map.of(USERNAME_SEARCH_PARAM, username));
    }

    List<UserAccount<ID>> searchForUsers(Map<String, String> params);

    List<UserAccount<ID>> searchForUsers(Map<String, String> params, int page, int pageSize);
}