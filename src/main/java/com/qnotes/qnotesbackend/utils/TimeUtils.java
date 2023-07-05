package com.qnotes.qnotesbackend.utils;

import java.sql.Timestamp;
import java.util.Date;

public class TimeUtils {
    private TimeUtils() {}
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp((new Date()).getTime());
    }
}
