package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T lt, T start, T end) {
        return lt.compareTo(start) >= 0 && lt.compareTo(end) < 0;
    }

    public static LocalDate parseDate(String date) {
        return date.isEmpty() ? null : LocalDate.parse(date);
    }

    public static LocalTime parseTime(String time) {
        return time.isEmpty() ? null : LocalTime.parse(time);
    }

    public static LocalDate replacingEmptyDate(LocalDate date, boolean isLowerBorder) {
        return date == null ? (isLowerBorder ? LocalDate.MIN : LocalDate.MAX) : date;
    }

    public static LocalTime replacingEmptyTime(LocalTime time, boolean isLowerBorder) {
        return time == null ? (isLowerBorder ? LocalTime.MIN : LocalTime.MAX) : time;
    }

    public static LocalDateTime convertToLocalDateTime(LocalDate date, boolean isLowerBorder) {
        return LocalDateTime.of(date, isLowerBorder ? LocalTime.MIN : LocalTime.MAX);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

