package com.app.ims.common;

public class Constants {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHORITIES_KEY = "scopes";
    public static final String ROLE = "role";
    public static final String SECRET_KEY = "secret_secret_secret";
    public static final String USER_ROLE = "USER";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String PASSWORD_REGEXP = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    public static final String NAME_REGEXP = "^([a-zA-Z]{2,}\\s[a-zA-Z]{1,}'?-?[a-zA-Z]{2,}\\s?([a-zA-Z]{1,})?)";
    public static final Long twoWeeks = Long.valueOf(1296000000);
    public static final Long twoMinutes = Long.valueOf(120);
    public static final Long TokenExpiration = Long.valueOf(twoWeeks);
}
