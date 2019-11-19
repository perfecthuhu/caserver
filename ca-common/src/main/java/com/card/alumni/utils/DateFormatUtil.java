package com.card.alumni.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期格式化工具类
 */
public final class DateFormatUtil {

    /** 日期时间转换器, 格式: <tt>yyyy-MM-dd HH:mm:ss</tt> */
    public static final FastDateFormat ISO_DATETIME_NO_T_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    /** 日期时间转换器, 带毫秒 格式: <tt>yyyy-MM-dd HH:mm:ss.SSS</tt> */
    public static final FastDateFormat ISO_DATETIME_NO_T_MIL_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSS");

    /** 日期转换器, 格式: <tt>yyyy-MM-dd</tt> */
    public static final FastDateFormat ISO_DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");

    /** 时间转换器, 格式: <tt>HH:mm:ss</tt> */
    public static final FastDateFormat ISO_TIME_NO_T_FORMAT = FastDateFormat.getInstance("HH:mm:ss");

    /** 时间转换器, 带毫秒, 格式: <tt>HH:mm:ss</tt> */
    public static final FastDateFormat ISO_TIME_NO_T_MIL_FORMAT = FastDateFormat.getInstance("HH:mm:ss.SSS");

    private DateFormatUtil() {
    }

    /* -------------------------- 毫秒 转 字符串 无异常 -------------------------- */
    /**
     * 将毫秒值转成 <tt>yyyy-MM-dd</tt> 格式
     *
     * @param mills 精确到毫秒的时间戳
     *
     * @return null 或者 类似"2018-03-14", 不会抛出异常
     */
    public static String toYMD(final long mills) {
        return formatWithoutException(new Date(mills), ISO_DATE_FORMAT.getPattern());
    }
    /**
     * 将毫秒值转成 <tt>HH:mm:ss</tt> 格式
     *
     * @param mills 精确到毫秒的时间戳
     *
     * @return null 或者 类似"18:06:12", 不会抛出异常
     */
    public static String toHMS(final long mills) {
        return formatWithoutException(new Date(mills), ISO_TIME_NO_T_FORMAT.getPattern());
    }
    /**
     * 将毫秒值转成 <tt>yyyy-MM-dd HH:mm:ss</tt> 格式
     *
     * @param mills 精确到毫秒的时间戳
     *
     * @return null 或者 类似"2018-03-14 18:06:12", 不会抛出异常
     */
    public static String toYMDHMS(final long mills) {
        return formatWithoutException(new Date(mills), ISO_DATETIME_NO_T_FORMAT.getPattern());
    }
    /**
     * 将毫秒值转成 <tt>HH:mm:ss.SSS</tt> 格式
     *
     * @param mills 精确到毫秒的时间戳
     *
     * @return null 或者 类似"18:06:12.496", 不会抛出异常
     */
    public static String toHMSS(final long mills) {
        return formatWithoutException(new Date(mills), ISO_TIME_NO_T_MIL_FORMAT.getPattern());
    }
    /**
     * 将毫秒值转成 <tt>yyyy-MM-dd HH:mm:ss.SSS</tt> 格式
     *
     * @param mills 精确到毫秒的时间戳
     *
     * @return null 或者 类似"2018-03-14 18:06:12.496", 不会抛出异常
     */
    public static String toYMDHMSS(final long mills) {
        return formatWithoutException(new Date(mills), ISO_DATETIME_NO_T_MIL_FORMAT.getPattern());
    }

    /* -------------------------- Date 转 字符串 -------------------------- */
    /**
     * 将Date转成 <tt>yyyy-MM-dd</tt> 格式
     *
     * @param date 待转换的日期对象
     *
     * @return null 或者 类似"2018-03-14", 不会抛出异常
     */
    public static String toYMD(final Date date) {
        return formatWithoutException(date, ISO_DATE_FORMAT.getPattern());
    }
    /**
     * 将Date转成 <tt>HH:mm:ss</tt> 格式
     *
     * @param date 待转换的日期对象
     *
     * @return null 或者 类似"18:06:12", 不会抛出异常
     */
    public static String toHMS(final Date date) {
        return formatWithoutException(date, ISO_TIME_NO_T_FORMAT.getPattern());
    }
    /**
     * 将Date转成 <tt>yyyy-MM-dd HH:mm:ss</tt> 格式
     *
     * @param date 待转换的日期对象
     *
     * @return null 或者 类似"2018-03-14 18:06:12", 不会抛出异常
     */
    public static String toYMDHMS(final Date date) {
        return formatWithoutException(date, ISO_DATETIME_NO_T_FORMAT.getPattern());
    }
    /**
     * 将Date转成 <tt>HH:mm:ss.SSS</tt> 格式
     *
     * @param date 待转换的日期对象
     *
     * @return null 或者 类似"18:06:12.496", 不会抛出异常
     */
    public static String toHMSS(final Date date) {
        return formatWithoutException(date, ISO_TIME_NO_T_MIL_FORMAT.getPattern());
    }
    /**
     * 将Date转成 <tt>yyyy-MM-dd HH:mm:ss.SSS</tt> 格式
     *
     * @param date 待转换的日期对象
     *
     * @return null 或者 类似"2018-03-14 18:06:12.496", 不会抛出异常
     */
    public static String toYMDHMSS(final Date date) {
        return formatWithoutException(date, ISO_DATETIME_NO_T_MIL_FORMAT.getPattern());
    }

    /* -------------------------- 字符串 转 Date 无异常 -------------------------- */
    /**
     * 按 <tt>yyyy-MM-dd</tt> 格式解析日期字符串
     *
     * @param dateStr 待解析的日期字符串, 为空时返回null
     *
     * @return null 或者 Date对象, 不会抛出异常
     */
    public static Date fromYMD(final String dateStr) {
        return parseWithoutException(dateStr, ISO_DATE_FORMAT.getPattern());
    }
    /**
     * 按 <tt>HH:mm:ss</tt> 格式解析日期字符串
     *
     * @param dateStr 待解析的日期字符串, 为空时返回null
     *
     * @return null 或者 Date对象, 不会抛出异常
     */
    public static Date fromHMS(final String dateStr) {
        return parseWithoutException(dateStr, ISO_TIME_NO_T_FORMAT.getPattern());
    }
    /**
     * 按 <tt>yyyy-MM-dd HH:mm:ss</tt> 格式解析日期字符串
     *
     * @param dateStr 待解析的日期字符串, 为空时返回null
     *
     * @return null 或者 Date对象, 不会抛出异常
     */
    public static Date fromYMDHMS(final String dateStr) {
        return parseWithoutException(dateStr, ISO_DATETIME_NO_T_FORMAT.getPattern());
    }
    /**
     * 按 <tt>HH:mm:ss.SSS</tt> 格式解析日期字符串
     *
     * @param dateStr 待解析的日期字符串, 为空时返回null
     *
     * @return null 或者 Date对象, 不会抛出异常
     */
    public static Date fromHMSS(final String dateStr) {
        return parseWithoutException(dateStr, ISO_TIME_NO_T_MIL_FORMAT.getPattern());
    }
    /**
     * 按 <tt>yyyy-MM-dd HH:mm:ss.SSS</tt> 格式解析日期字符串
     *
     * @param dateStr 待解析的日期字符串, 为空时返回null
     *
     * @return null 或者 Date对象, 不会抛出异常
     */
    public static Date fromYMDHMSS(final String dateStr) {
        return parseWithoutException(dateStr, ISO_DATETIME_NO_T_MIL_FORMAT.getPattern());
    }

    /* -------------------------- 毫秒/Date/Calendar 转 字符串, 可能抛异常 -------------------------- */
    /**
     * 使用指定模板格式化日期
     *
     * @param millis  精确到毫秒位的时间
     * @param pattern 模板, 例: "yyyy-MM-dd HH:mm:ss.SSS"
     *
     * @return null 或者 类似"2018-03-14 18:06:12.496"
     */
    public static String format(final long millis, final String pattern) {
        return format(new Date(millis), pattern);
    }
    /**
     * 使用指定模板格式化日期
     *
     * @param cal     待格式化的时间
     * @param pattern 模板, 例: "yyyy-MM-dd HH:mm:ss.SSS"
     *
     * @return null 或者 类似"2018-03-14 18:06:12.496"
     */
    public static String format(final Calendar cal, final String pattern) {
        return format(cal.getTime(), pattern);
    }
    /**
     * 使用指定模板格式化日期
     *
     * @param date    待格式化的日期对象
     * @param pattern 模板, 例: "yyyy-MM-dd HH:mm:ss.SSS"
     *
     * @return null 或者 类似"2018-03-14 18:06:12.496"
     */
    public static String format(final Date date, final String pattern) {
        return format(date, pattern, null, null);
    }

    /* -------------------------- 字符串 转 Date, 可能抛异常 -------------------------- */
    /**
     * 按指定格式将字符串解析为Date
     *
     * @param dateStr 待解析的日期字符串
     * @param pattern 解析模板, 例: "yyyy-MM-dd HH:mm:ss.SSS".
     *
     * @throws ParseException
     */
    public static Date parse(final String dateStr, final String pattern) throws ParseException {
        return parse(dateStr, pattern, null, null);
    }

    private static String formatWithoutException(final Date date, final String pattern) {
        if (StringUtils.isBlank(pattern) || date == null) {
            return null;
        }
        try {
            return format(date, pattern, null, null);
        }
        catch (Exception e) {
            return null;
        }
    }

    private static String format(final Date date, final String pattern, final TimeZone timeZone, final Locale locale) {
        final FastDateFormat df = FastDateFormat.getInstance(pattern, timeZone, locale);
        return df.format(date);
    }

    private static Date parseWithoutException(final String dateStr, final String pattern) {
        if (StringUtils.isAnyBlank(dateStr, pattern)) {
            return null;
        }

        try {
            return parse(dateStr, pattern, null, null);
        }
        catch (Exception e) {
            return null;
        }
    }

    private static Date parse(final String dateStr, final String pattern, final TimeZone timeZone, final Locale locale) throws ParseException {
        final FastDateFormat df = FastDateFormat.getInstance(pattern, timeZone, locale);
        return df.parse(dateStr);
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(fromYMDHMSS("2018-03-18 12:30:24.232"));
        System.out.println(toYMDHMSS(System.currentTimeMillis()));
        System.out.println(parse("18/03/18 23:00:23.123", "yy/MM/dd HH:mm:ss.SSS"));
        System.out.println(format(new Date(), "yy/MM/dd HH:mm:ss.SSS"));
    }
}
