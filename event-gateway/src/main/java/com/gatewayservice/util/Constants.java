package com.gatewayservice.util;

public final class Constants {

    private Constants() {
    }

    // Event Status
    public static final String RECEIVED = "RECEIVED";
    public static final String PROCESSED = "PROCESSED";
    public static final String FAILED = "FAILED";

    // Messages
    public static final String EVENT_RECEIVED = "Event received successfully";
    public static final String EVENT_PROCESSED = "Event processed successfully";
    public static final String DUPLICATE_EVENT = "Duplicate event received";
    public static final String ACCOUNT_SERVICE_ERROR = "Unable to process event";
}