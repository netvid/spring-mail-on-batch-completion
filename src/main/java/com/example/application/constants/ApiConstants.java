package com.example.application.constants;

public final class ApiConstants {
    public ApiConstants(){
        throw new IllegalStateException("Utility class");
    }
    public static final String BASE_URL = "/api";
    public static final String CURRENT_VERSION = "/v1";
    public static final String[] BATCH_DEVICE_HEADERS = {"id","name"};
    public static final int BATCH_DEFAULT_CHUNK_SIZE = 50;
    public static String BATCH_DEVICE_KEY_1 = "PROCESSED_ITEMS";
}
