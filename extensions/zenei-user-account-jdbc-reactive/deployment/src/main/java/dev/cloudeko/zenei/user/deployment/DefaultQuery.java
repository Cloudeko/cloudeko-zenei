package dev.cloudeko.zenei.user.deployment;

import dev.cloudeko.zenei.user.QueryRegistry;

public enum DefaultQuery {
    /**
     * User Account Queries
     */
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
    USER_ACCOUNT_FIND_BY_PRIMARY_EMAIL(QueryMetadata.of(
            "SELECT id, username, created_at, updated_at FROM zenei_user_account WHERE id = (SELECT user_id FROM zenei_user_email_addresses WHERE email = %s AND primary_email = true)",
            1,
            QueryRegistry.USER_ACCOUNT_FIND_BY_PRIMARY_EMAIL_ADDRESS
    )),
    USER_ACCOUNT_FIND_BY_PRIMARY_PHONE_NUMBER(QueryMetadata.of(
            "SELECT id, username, created_at, updated_at FROM zenei_user_account WHERE id = (SELECT user_id FROM zenei_user_phone_numbers WHERE phone_number = %s AND primary_phone_number = true)",
            1,
            QueryRegistry.USER_ACCOUNT_FIND_BY_PRIMARY_PHONE_NUMBER
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
    )),

    /**
     * User Account Email Addresses Queries
     */
    USER_ACCOUNT_LIST_EMAIL_ADDRESSES(QueryMetadata.of(
            "SELECT id, user_id, email, email_verified, primary_email, created_at, updated_at FROM zenei_user_email_addresses WHERE user_id = %s",
            1,
            QueryRegistry.USER_ACCOUNT_EMAIL_ADDRESSES_LIST_BY_USER_ID
    )),
    USER_ACCOUNT_EMAIL_ADDRESSES_CREATE(QueryMetadata.of(
            "INSERT INTO zenei_user_email_addresses (user_id, email, email_verified, primary_email, created_at, updated_at) VALUES (%s, %s, %s, %s, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) RETURNING id, user_id, email, email_verified, primary_email, created_at, updated_at",
            4,
            QueryRegistry.USER_ACCOUNT_EMAIL_ADDRESSES_CREATE
    )),
    USER_ACCOUNT_EMAIL_ADDRESSES_UPDATE(QueryMetadata.of(
            "UPDATE zenei_user_email_addresses SET email = %s, email_verified = %s, primary_email = %s, updated_at = CURRENT_TIMESTAMP WHERE id = %s RETURNING id, user_id, email, email_verified, primary_email, created_at, updated_at",
            4,
            QueryRegistry.USER_ACCOUNT_EMAIL_ADDRESSES_UPDATE
    )),
    USER_ACCOUNT_EMAIL_ADDRESSES_DELETE(QueryMetadata.of(
            "DELETE FROM zenei_user_email_addresses WHERE user_id = %s AND email = %s",
            2,
            QueryRegistry.USER_ACCOUNT_EMAIL_ADDRESSES_DELETE
    )),
    USER_ACCOUNT_EMAIL_ADDRESSES_DELETE_BY_USER_ID(QueryMetadata.of(
            "DELETE FROM zenei_user_email_addresses WHERE user_id = %s",
            1,
            QueryRegistry.USER_ACCOUNT_EMAIL_ADDRESSES_DELETE_BY_USER_ID
    ));

    private final QueryMetadata metadata;

    DefaultQuery(QueryMetadata metadata) {
        this.metadata = metadata;
    }

    public QueryMetadata getMetadata() {
        return metadata;
    }
}
