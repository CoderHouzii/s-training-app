package songming.straing.utils;

import android.support.annotation.StringDef;

import com.socks.library.KLog;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * 时间工具类
 */
public class TimeUtils {
    private TimeUtils() {}

    /**
     * 比较传入的时间是否在当前系统时间之后
     *
     * @param time 传入格式 - 小时:分钟
     * @return true：传入时间在当前系统时间之后,false反之
     */
    public static boolean compareTimeWithHM(String time) {
        String[] input = time.split(":");
        if (input[0].equals("00")) {
            input[0] = "24";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                Integer.parseInt(input[0]), Integer.parseInt(input[1]), 0);
        KLog.d("time", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(calendar.getTime()));
        long inputTimeMillions = calendar.getTimeInMillis();
        KLog.d("compare", "input>>>  " + inputTimeMillions + "       current>>>   " + System.currentTimeMillis());
        return inputTimeMillions > System.currentTimeMillis();
    }

    public static long string2Time(String time) {
        String[] input = time.split(":");
        if (input[0].equals("00")) {
            input[0] = "24";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                Integer.parseInt(input[0]), Integer.parseInt(input[1]), 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 比较传入时间与现在时间是否处于同一天
     *
     * true:当前时间比传入时间大 传入时间为昨天
     * false:当前时间不比传入时间大 传入时间为今天
     */
    public static boolean isLastDay(long inputTime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(inputTime);

        Calendar current = Calendar.getInstance();
        current.setTimeInMillis(System.currentTimeMillis());

        KLog.d("day", "today>>>>  " + current.get(Calendar.DAY_OF_MONTH) + "       inputDay>>>" +
                c.get(Calendar.DAY_OF_MONTH));
        return current.get(Calendar.DAY_OF_MONTH) > c.get(Calendar.DAY_OF_MONTH);
    }


    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ yyyy_MM_dd_HH_mm_ss_SSS, yyyy_MM_dd_HH_mm_ss, yyyy_MM_dd_HH_mm, yyyy_MM_dd, yyyynMMyddr,
            yyyy_M_d_H_m_s_S, yyyy_M_d_H_m_s ,yyyy_M_d_H_m,yyyy_M_d,yyyyMMdd_HH_mm_ss_SSS,
            yyyyMMdd_HH_mm_ss,yyyyMMdd_HH_mm,yyyyMMdd,yyyyMMddHHmmssSSS,yyyyMMddHHmmss,yyyyMMddHHmm,yyyyMdHm_zh})
    public @interface StringFormat{}

    /**
     * 转换格式为yyyy-MM-dd HH:mm:ss:SSS。
     */ public final static String yyyy_MM_dd_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss:SSS";

    /**
     * 转换格式为yyyy-MM-dd HH:mm:ss。
     */
    public final static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

    /**
     * 转换格式为yyyy-MM-dd HH:mm。
     */
    public final static String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";

    /**
     * 转换格式为yyyy-MM-dd。
     */
    public final static String yyyy_MM_dd = "yyyy-MM-dd";

    /**
     * 转换格式为yyyy年MM月DD日
     */
    public final static String yyyynMMyddr = "yyyy年MM月dd日";
    /**
     * 转换格式为yyyy-M-d H:m:s:S
     */
    public final static String yyyy_M_d_H_m_s_S = "yyyy-M-d H:m:s:S";

    /**
     * 转换格式为yyyy-M-d H:m:s
     */
    public final static String yyyy_M_d_H_m_s = "yyyy-M-d H:m:s";

    /**
     * 转换格式为yyyy-M-d H:m
     */
    public final static String yyyy_M_d_H_m = "yyyy-M-d H:m";

    /**
     * 转换成格式为yyyy-M-d
     */
    public final static String yyyy_M_d = "yyyy-M-d";

    /**
     * 转换格式为yyyyMMdd HH:mm:ss:SSS。
     */
    public final static String yyyyMMdd_HH_mm_ss_SSS = "yyyyMMdd HH:mm:ss:SSS";

    /**
     * 转换格式为yyyyMMdd HH:mm:ss。
     */
    public final static String yyyyMMdd_HH_mm_ss = "yyyyMMdd HH:mm:ss";

    /**
     * 转换格式为yyyyMMdd HH:mm。
     */
    public final static String yyyyMMdd_HH_mm = "yyyyMMdd HH:mm";

    /**
     * 转换格式为yyyyMMdd。
     */
    public final static String yyyyMMdd = "yyyyMMdd";

    /**
     * 转换格式为yyyyMMddHHmmssSSS。
     */
    public final static String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";

    /**
     * 转换格式为yyyyMMddHHmmss。
     */
    public final static String yyyyMMddHHmmss = "yyyyMMddHHmmss";

    /**
     * 转换格式为yyyyMMddHHmm。
     */
    public final static String yyyyMMddHHmm = "yyyyMMddHHmm";

    /**
     * 转换格式为yyyy年M月d日H点m分。
     */
    public final static String yyyyMdHm_zh = "yyyy年M月d日H点m分";

    private int weeks = 0;

    public static String longToString(@StringFormat String formatStr, long milliseconds) {
        return new SimpleDateFormat(formatStr, Locale.getDefault()).format(new Date(milliseconds));
    }

    /**
     * 将String类型的日期转换为Date对象。
     *
     * @param dateString 代表日期的字符串。
     * @param style 转换格式。
     * @return 日期对象。
     * @throws ParseException 日期字符串不满足指定转换格式时抛出的异常。
     */
    public static Date stringToDate(String dateString, String style) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(style, Locale.CHINESE);
        return format.parse(dateString);
    }

    /**
     * 将Date转换成为指定格式的String。
     *
     * @param date 日期对象。
     * @param style 转换格式。
     * @return 代表日期的字符串。
     */
    public static String dateToString(Date date, String style) {
        if (null == date) return "";

        SimpleDateFormat format = new SimpleDateFormat(style, Locale.CHINESE);
        return format.format(date);
    }

    public static int getIntervalDays(Date startday, Date endday) {
        if (startday.after(endday)) {
            Date cal = startday;
            startday = endday;
            endday = cal;
        }
        long sl = startday.getTime();
        long el = endday.getTime();
        long ei = el - sl;
        return (int) (ei / (1000 * 60 * 60 * 24));
    }

    /**
     * 将日期字符串从源格式转换成为目标格式。
     *
     * @param dateString 日期字符串。
     * @param sourceStyle 源格式。
     * @param tagetStyle 目标格式。
     * @return 转换成为目标格式后的字符串。
     * @throws ParseException 转换格式与指定字符串不符时抛出的异常。
     */
    public static String stringToString(String dateString, String sourceStyle, String tagetStyle)
            throws ParseException {
        Date date = stringToDate(dateString, sourceStyle);
        return dateToString(date, tagetStyle);
    }

    /**
     * 返回某个日期的前几天或者后几天
     *
     * @param dateString 日期字符串
     * @param num 前后多少天
     * @param dateStyle 输入格式
     * @throws ParseException
     */
    public static String getSomeDate(String dateString, int num, String dateStyle) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateStyle);
        Calendar day = Calendar.getInstance();
        try {
            day.setTime(sdf.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        day.add(Calendar.DATE, num);
        return sdf.format(day.getTime());
    }

    /**
     * 计算两个日期之间的天数
     */
    public static int daysOfTwoDate(Date beginDate, Date endDate, String dateStyle) {
        int days = 0;// 两个日期之间的天数
        DateFormat df = new SimpleDateFormat(dateStyle);

        Calendar beginCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        beginCalendar.setTime(beginDate);
        endCalendar.setTime(endDate);
        // 计算天数
        while (beginCalendar.before(endCalendar)) {
            days++;
            beginCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return days == 0 ? 1 : days;
    }

    /**
     * 根据输入两个日期,获取这段时间内对应的月份列表
     */
    public static List getListMonth(Date beginDate, Date endDate) {
        List list = new ArrayList();
        Calendar beginCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        beginCal.setTime(beginDate);
        while (beginCal.before(endCal)) {
            list.add(format(beginCal.getTime(), "yyyy-MM"));
            beginCal.add(Calendar.MONTH, 1);
        }
        if (beginCal.get(Calendar.MONTH) == endCal.get(Calendar.MONTH)) {
            list.add(format(beginCal.getTime(), "yyyy-MM"));
        }
        return list;
    }

    public static String getSystemTime() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
        return df.format(new Date()).toString();
    }

    public static String getNowTime() {
        Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return format.format(date);
    }

    /**
     *
     * @param dateString
     * @param dateStyle
     * @return
     */
    public static Calendar getCalendarDate(String dateString, String dateStyle) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateStyle);

        Calendar day = Calendar.getInstance();
        try {
            Date date = sdf.parse(dateString);
            date.setTime(date.getTime() + 8 * 60 * 60 * 1000);
            day.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }

    /**
     * 时间差计算
     *
     * @param beforeTime 比较前时间
     * @param currentTime 当前系统时间
     * @param time 时间差值
     */
    public static boolean dateCompare(String beforeTime, String currentTime, int time) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
            boolean temp = false;
            Date begin = df.parse(beforeTime);
            Date end = df.parse(currentTime);
            long between = (end.getTime() - begin.getTime()) / 1000;//除以1000是为了转换成秒
            long day = between / (24 * 3600);
            long hour = between % (24 * 3600) / 3600;
            long minute = between % 3600 / 60;
            long second = between % 60 / 60;
            if ((hour == 0) && (day == 0) && (minute <= time)) {
                temp = true;
            }
            return temp;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * DOUBLE 数据类型四舍五入
     *
     * @param num 保留小数点后几位
     */
    public static double changeDecimal(int num, double value) {
        BigDecimal b = new BigDecimal(value);
        double v = b.setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue();//   表明四舍五入，保留两位小数
        return v;
    }

    /**
     * 根据加减天数计算日期
     *
     * @param nativeDate 需要计算的日期
     * @param calDay 加减天数
     * @return 计算后的日期
     */
    public static String getSpecificDateByDay(Date nativeDate, int calDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nativeDate);
        cal.add(Calendar.DATE, calDay);

        Date specificDate = cal.getTime();
        String specificDateStr = TimeUtils.dateToString(specificDate, "yyyy-MM-dd");

        return specificDateStr;
    }

    public static String format(Date date, String format) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 计算当月最后一天,返回字符串
     */
    public static String getDefaultDay() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//设为当前月的1号
        lastDate.add(Calendar.MONTH, 1);//加一个月，变为下月的1号
        lastDate.add(Calendar.DATE, -1);//减去一天，变为当月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 当月第一天
     */
    public static String getOneMonthDay() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//设为当前月的1号
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获得本周第一天日期
     */
    public static String getMondayOFWeek() {
        Calendar c = Calendar.getInstance();
        int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayofweek == 0) dayofweek = 7;
        c.add(Calendar.DATE, -dayofweek + 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        return sdf.format(c.getTime());
    }

    /**
     * 获得本周最后一天
     */
    public static String getSaturday() {
        Calendar c = Calendar.getInstance();
        int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayofweek == 0) dayofweek = 7;
        c.add(Calendar.DATE, -dayofweek + 7);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        return sdf.format(c.getTime());
    }

    /**
     * 获得当前日期是第几周
     */
    public static int getWeek() {
        Calendar c = Calendar.getInstance();
        //c.setTime(Date date);你也可以设置为其他时间
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获得日期周数
     */
    public static int getCountWeek() {
        Calendar calendar = Calendar.getInstance();
        int max = calendar.getActualMaximum(Calendar.WEEK_OF_YEAR);
        return max;
    }
}
