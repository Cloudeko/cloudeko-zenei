package dev.cloudeko.zenei.user.deployment;

import dev.cloudeko.zenei.user.QueryRegistry;

public enum DefaultQuery {
    USER_ACCOUNT_FIND_BY_IDENTIFIER(QueryMetadata.of(
            "SELECT id, username, created_at, updated_at FROM zenei_user_account WHERE id = %s",
            1,
            QueryRegistry.USER_ACCOUNT_FIND_BY_IDENTIFIER
    )),
    USER_ACCOUNT_FIND_BY_USERNAME(QueryMetadata.of(
            "SELECT id, username, created_at, updated_at FROM zenei_user_account WHERE username = %s",
            1,
            QueryRegistry.USER_ACCOUNT_FIND_BY_USERNAME
    )),
    USER_ACCOUNT_LIST(QueryMetadata.of(
            "SELECT id, username, created_at, updated_at FROM zenei_user_account",
            0,
            QueryRegistry.USER_ACCOUNT_LIST
    )),
    USER_ACCOUNT_LIST_PAGINATED(QueryMetadata.of(
            "SELECT id, username, created_at, updated_at FROM zenei_user_account LIMIT %s OFFSET %s",
            2,
            QueryRegistry.USER_ACCOUNT_LIST_PAGINATED
    )),
    USER_ACCOUNT_CREATE(QueryMetadata.of(
            "INSERT INTO zenei_user_account (username, created_at, updated_at) VALUES (%s, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) RETURNING id, username, created_at, updated_at",
            1,
            QueryRegistry.USER_ACCOUNT_CREATE
    )),
    USER_ACCOUNT_UPDATE(QueryMetadata.of(
            "UPDATE zenei_user_account SET username = %s, updated_at = CURRENT_TIMESTAMP WHERE id = %s RETURNING id, username, created_at, updated_at",
            2,
            QueryRegistry.USER_ACCOUNT_UPDATE
    )),
    USER_ACCOUNT_DELETE(QueryMetadata.of(
            "DELETE FROM zenei_user_account WHERE id = %s RETURNING id, username, image, created_at, updated_at",
            1,
            QueryRegistry.USER_ACCOUNT_DELETE
    ));

    private final QueryMetadata metadata;

    DefaultQuery(QueryMetadata metadata) {
        this.metadata = metadata;
    }

    public QueryMetadata getMetadata() {
        return metadata;
    }
}
