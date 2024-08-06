package com.abdok.snapnotes.Utils;

import android.annotation.SuppressLint;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@SuppressLint({"NewApi", "LocalSuppress"})
public class TimeUtils {

    public static String getRelativeTime(long timestamp) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime noteTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());

        long minutesBetween = ChronoUnit.MINUTES.between(noteTime, now);
        long hoursBetween = ChronoUnit.HOURS.between(noteTime, now);
        long daysBetween = ChronoUnit.DAYS.between(noteTime, now);

        if (minutesBetween < 1) {
            return "Just now";
        } else if (minutesBetween < 60) {
            return minutesBetween + " Minutes ago";
        } else if (hoursBetween < 24) {
            return hoursBetween + " Hours ago";
        } else if (daysBetween == 1) {
            return "Yesterday";
        } else if (daysBetween < 7) {
            return daysBetween + " Days ago";
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
            return noteTime.format(formatter);
        }
    }
}