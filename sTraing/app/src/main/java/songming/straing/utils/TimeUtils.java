package songming.straing.utils;

import com.socks.library.KLog;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        if (input[0].equals("00")){
            input[0]="24";
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
        if (input[0].equals("00")){
            input[0]="24";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                Integer.parseInt(input[0]), Integer.parseInt(input[1]), 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 比较传入时间与现在时间是否处于同一天
     *
     * ture:当前时间比传入时间大 传入时间为昨天
     * false:当前时间不比传入时间大 传入时间为今天
     * @param inputTime
     * @return
     */
    public static boolean compareCurrentTime(long inputTime){
        Calendar c=Calendar.getInstance();
        c.setTimeInMillis(inputTime);

        Calendar current=Calendar.getInstance();
        current.setTimeInMillis(System.currentTimeMillis());

        return c.get(Calendar.DAY_OF_MONTH)>current.get(Calendar.DAY_OF_MONTH);
    }
}
