package com.qnotes.qnotesbackend.utils;

import java.util.UUID;

public class UUIDUtils {
    private UUIDUtils() {}
    public static UUID fromString(String string) {
        if (string == null) {
            return null;
        } else {
            return UUID.fromString(string);
        }
    }
}
