package com.sep.common.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class DateUtils {

    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 判断某个时间点是否在某个时间段内
     *
     * @param start    开始时间
     * @param end      结束时间
     * @param dateTime 时间点
     * @return 是否在这个时间段内
     */
    public static boolean dateTimeInQuantum(LocalDateTime start, LocalDateTime end, LocalDateTime dateTime) {
        return start.isAfter(dateTime) || end.isBefore(dateTime)
                || start.isEqual(dateTime) || end.isEqual(dateTime);
    }

    /**
     * 判断两个时间段是否交叉
     *
     * @param start  开始时间
     * @param end    结束时间
     * @param start1 开始时间
     * @param end1   结束时间1
     * @return 是否交叉
     */
    public static boolean isCross(LocalDateTime start, LocalDateTime end, LocalDateTime start1, LocalDateTime end1) {
        return dateTimeInQuantum(start, end, start1) || dateTimeInQuantum(start, end, end1);
    }

    /**
     * 获取今天的开始时间
     * @param date 请求时间
     * @return 时间
     */
    public static LocalDateTime getDayFirstTime(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MIN);
    }

    /**
     * 获取今天的结束时间
     * @param date 请求时间
     * @return 时间
     */
    public static LocalDateTime getDayEndTime(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MAX);
    }

    /**
     * 获取本周的开始时间
     * @param date 请求时间
     * @return 时间
     */
    public static LocalDateTime getWeekFirstTime(LocalDate date) {
        return LocalDateTime.of(date.with(DayOfWeek.MONDAY), LocalTime.MIN);
    }

    /**
     * 获取本周的结束时间
     * @param date 请求时间
     * @return 时间
     */
    public static LocalDateTime getWeekLastTime(LocalDate date) {
        return LocalDateTime.of(date.with(DayOfWeek.SUNDAY), LocalTime.MAX);
    }

    /**
     * 获取本月的开始时间
     * @param date 请求时间
     * @return 时间
     */
    public static LocalDateTime getMonthFirstTime(LocalDate date) {
        return LocalDateTime.of(date.with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
    }

    /**
     * 获取本月的结束时间
     *
     * @param date 请求时间
     * @return 时间
     */
    public static LocalDateTime getMonthLastTime(LocalDate date) {
        return LocalDateTime.of(date.with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);
    }

    /**
     * 时间格式化
     *
     * @param localDateTime 时间
     * @return 格式化后时间
     */
    public static String format(LocalDateTime localDateTime) {
        return dateTimeFormatter.format(localDateTime);
    }

}