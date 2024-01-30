package ru.clevertec.house.constant;


public class Constant {

    // pagination
    public static final String OFFSET_DEFAULT = "0";
    public static final String LIMIT_DEFAULT = "15";

    // cache
    public static final String LRU = "lru";
    public static final String LFU = "lfu";
    public static final float LOAD_FACTOR = 1.0f;
    public static final float CAPACITY_FACTOR = 1.5f;

    // error
    public static final String GENERATION_TOKEN_ERROR = "Error while generating token";
    public static final String VALIDATING_TOKEN_ERROR = "Error while validating token";
    public static final String USERNAME_NOT_EXIST = "Username not exists";
    public static final String USER_ROLE = "ROLE_USER";
    public static final String ADMIN_ROLE = "ROLE_ADMIN";
}
