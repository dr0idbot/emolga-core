package io.droidbot.emolga.auth;

public final class AuthConstants {

    public static final String USER_TABLE = "app_user";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_UPDATED_AT = "updated_at";

    public static final int USERNAME_MAX_LENGTH = 50;
    public static final int PASSWORD_MAX_LENGTH = 255;

    public static final String LOGIN_TITLE = "Sign In";
    public static final String LOGIN_USERNAME = "Username";
    public static final String LOGIN_PASSWORD = "Password";
    public static final String LOGIN_BUTTON = "Login";
    public static final String LOGIN_FAILED = "Invalid username or password";
    public static final String LOGIN_SUCCESS = "Login successful";

    public static final String JWT_COOKIE_NAME = "jwt";

    public static final String ROUTE_LOGIN = "login";

    private AuthConstants() {
    }
}
