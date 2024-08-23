package dev.cloudeko.zenei.user;

public class QueryRegistry {
    public static final String USER_ACCOUNT_FIND_BY_IDENTIFIER = "user-account-find-by-identifier";
    public static final String USER_ACCOUNT_FIND_BY_USERNAME = "user-account-find-by-username";
    public static final String USER_ACCOUNT_FIND_BY_PRIMARY_EMAIL_ADDRESS = "user-account-find-by-primary-email";
    public static final String USER_ACCOUNT_FIND_BY_PRIMARY_PHONE_NUMBER = "user-account-find-by-primary-phone-number";
    public static final String USER_ACCOUNT_LIST = "user-account-list";
    public static final String USER_ACCOUNT_LIST_PAGINATED = "user-account-list-paginated";
    public static final String USER_ACCOUNT_CREATE = "user-account-create";
    public static final String USER_ACCOUNT_UPDATE = "user-account-update";
    public static final String USER_ACCOUNT_DELETE = "user-account-delete";

    public static final String USER_ACCOUNT_EMAIL_ADDRESSES_LIST_BY_USER_ID = "user-account-email-addresses-find-by-user-id";
    public static final String USER_ACCOUNT_EMAIL_ADDRESSES_CREATE = "user-account-email-addresses-create";
    public static final String USER_ACCOUNT_EMAIL_ADDRESSES_UPDATE = "user-account-email-addresses-update";
    public static final String USER_ACCOUNT_EMAIL_ADDRESSES_DELETE = "user-account-email-addresses-delete";
    public static final String USER_ACCOUNT_EMAIL_ADDRESSES_DELETE_BY_USER_ID = "user-account-email-addresses-delete-by-user-id";
    public static final String USER_ACCOUNT_EMAIL_ADDRESSES_SET_PRIMARY = "user-account-email-addresses-set-primary";
    public static final String USER_ACCOUNT_EMAIL_ADDRESSES_SET_VERIFIED = "user-account-email-addresses-set-verified";
}
