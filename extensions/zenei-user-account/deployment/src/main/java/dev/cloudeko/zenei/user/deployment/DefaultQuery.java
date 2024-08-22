package dev.cloudeko.zenei.user.deployment;

import dev.cloudeko.zenei.user.QueryRegistry;

public enum DefaultQuery {
    USER_ACCOUNT_FIND_BY_IDENTIFIER(QueryMetadata.of(
            "SELECT id, username, image, created_at, updated_at FROM user_account WHERE id = ?",
            1,
            QueryRegistry.USER_ACCOUNT_FIND_BY_IDENTIFIER
    )),
    USER_ACCOUNT_FIND_BY_USERNAME(QueryMetadata.of(
            "SELECT id, username, image, created_at, updated_at FROM user_account WHERE username = ?",
            1,
            QueryRegistry.USER_ACCOUNT_FIND_BY_USERNAME
    )),
    USER_ACCOUNT_LIST(QueryMetadata.of(
            "SELECT id, username, image, created_at, updated_at FROM user_account",
            0,
            QueryRegistry.USER_ACCOUNT_LIST
    )),
    USER_ACCOUNT_LIST_PAGINATED(QueryMetadata.of(
            "SELECT id, username, image, created_at, updated_at FROM user_account LIMIT ? OFFSET ?",
            2,
            QueryRegistry.USER_ACCOUNT_LIST_PAGINATED
    )),
    USER_ACCOUNT_CREATE(QueryMetadata.of(
            "INSERT INTO user_account (username, image, created_at, updated_at) VALUES (?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) RETURNING id, username, image, created_at, updated_at",
            2,
            QueryRegistry.USER_ACCOUNT_CREATE
    )),
    USER_ACCOUNT_UPDATE(QueryMetadata.of(
            "UPDATE user_account SET username = ?, image = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ? RETURNING id, username, image, created_at, updated_at",
            3,
            QueryRegistry.USER_ACCOUNT_UPDATE
    )),
    USER_ACCOUNT_DELETE(QueryMetadata.of(
            "DELETE FROM user_account WHERE id = ? RETURNING id, username, image, created_at, updated_at",
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
