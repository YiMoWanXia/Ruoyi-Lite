package com.ruoyi.lite.core.base.common.utils;

import cn.hutool.core.date.LocalDateTimeUtil;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 时间工具类
 *
 * @author fooyao
 */
public class DateUtils {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static final String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return 当前日期
     */
    public static LocalDateTime getNowDate() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static String dateTimeNow(String format) {
        return LocalDateTimeUtil.format(LocalDateTime.now(), format);
    }

    public static String dateTime(LocalDateTime date) {
        return LocalDateTimeUtil.format(date, YYYY_MM_DD);
    }

    public static String parseDateToStr(String format, LocalDateTime date) {
        return LocalDateTimeUtil.format(date, format);
    }

    public static LocalDateTime dateTime(String format, String ts) {
        return LocalDateTimeUtil.parse(ts, format);
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static String datePath() {
        return LocalDateTimeUtil.format(LocalDateTime.now(), "yyyy/MM/dd");
    }

    /**
     * 获取服务器启动时间
     */
    public static LocalDateTime getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return LocalDateTime.ofInstant(
                Instant.ofEpochSecond(time),
                ZoneId.systemDefault() // 或用 ZoneId.of("Asia/Shanghai")
        );
    }

    /**
     * 计算时间差
     *
     * @param startDateTime 开始时间
     * @param endDateTime 结束时间
     * @return 时间差（天/小时/分钟）
     */
    public static String timeDistance(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Duration duration = LocalDateTimeUtil.between(startDateTime, endDateTime);
        return durationToReadable(duration);
    }

    /**
     * 将 Duration 转换为人类可读的字符串
     * 例如: 2天3小时45分钟30秒
     */
    public static String durationToReadable(Duration duration) {
        if (duration == null || duration.isZero()) {
            return "0秒";
        }

        long totalSeconds = duration.getSeconds();
        boolean negative = totalSeconds < 0;
        if (negative) {
            totalSeconds = -totalSeconds;
        }

        long days = totalSeconds / 86400;
        long hours = (totalSeconds % 86400) / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        long millis = duration.getNano() / 1_000_000;

        StringBuilder sb = new StringBuilder();
        if (negative) sb.append("-");

        if (days > 0) sb.append(days).append("天");
        if (hours > 0) sb.append(hours).append("小时");
        if (minutes > 0) sb.append(minutes).append("分钟");
        if (seconds > 0) sb.append(seconds).append("秒");
        if (millis > 0 && days == 0 && hours == 0) sb.append(millis).append("毫秒");

        return sb.toString();
    }
}
