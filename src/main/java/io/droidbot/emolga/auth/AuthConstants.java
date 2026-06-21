package io.droidbot.emolga.auth;

public final class AuthConstants {

    public static final String AUTH_SCHEMA = "emolga_auth";

    public static final String USER_TABLE = "emolga_user";
    public static final String ROLE_TABLE = "emolga_role";
    public static final String ROLE_PERMISSION_TABLE = "emolga_role_permission";
    public static final String USER_ROLE_TABLE = "emolga_user_role";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD_HASH = "password_hash";
    public static final String COLUMN_DISPLAY_NAME = "display_name";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_ENABLED = "enabled";
    public static final String COLUMN_ACCOUNT_LOCKED = "account_locked";
    public static final String COLUMN_EMAIL_VERIFIED = "email_verified";
    public static final String COLUMN_LAST_LOGIN_AT = "last_login_at";
    public static final String COLUMN_ROLE_NAME = "role_name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PERMISSION_KEY = "permission_key";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_ROLE_ID = "role_id";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_UPDATED_AT = "updated_at";

    public static final int USERNAME_MAX_LENGTH = 100;
    public static final int EMAIL_MAX_LENGTH = 255;
    public static final int PASSWORD_HASH_MAX_LENGTH = 255;
    public static final int DISPLAY_NAME_MAX_LENGTH = 150;
    public static final int FIRST_NAME_MAX_LENGTH = 100;
    public static final int LAST_NAME_MAX_LENGTH = 100;
    public static final int ROLE_NAME_MAX_LENGTH = 100;
    public static final int DESCRIPTION_MAX_LENGTH = 500;
    public static final int PERMISSION_KEY_MAX_LENGTH = 500;

    public static final String LOGIN_TITLE = "Sign In";
    public static final String LOGIN_USERNAME = "Username";
    public static final String LOGIN_PASSWORD = "Password";
    public static final String LOGIN_BUTTON = "Login";
    public static final String LOGIN_FAILED = "Invalid username or password";
    public static final String LOGIN_SUCCESS = "Login successful";

    public static final String JWT_COOKIE_NAME = "access_token";

    public static final String ROUTE_LOGIN = "login";

    private AuthConstants() {
    }
}
