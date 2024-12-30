package com.example.android.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUtil {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDateTime setTime(int hour, int minute) {
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.of(hour,minute);
        LocalDateTime localDateTime = LocalDateTime.of(localDate,localTime);
        return localDateTime;
    }
}
