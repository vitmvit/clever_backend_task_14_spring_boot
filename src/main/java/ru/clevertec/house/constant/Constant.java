package ru.clevertec.house.constant;


public class Constant {

    // pagination
    public static final String OFFSET_DEFAULT = "0";
    public static final String LIMIT_DEFAULT = "15";

    public static final String NAME_DEFAULT = "";
    public static final String SURNAME_DEFAULT = "";

    // cache
    public static final String LRU = "lru";
    public static final String LFU = "lfu";
    public static final float LOAD_FACTOR = 1.0f;
    public static final float CAPACITY_FACTOR = 1.5f;

    // error
    public static final String HOUSE_DELETE_ERROR = "House delete exception!";
    public static final String HOUSE_UPDATE_ERROR = "House update exception!";
    public static final String HOUSE_CREATE_ERROR = "House create exception!";
}
