package cn.com.ichile.bigbug;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/4/27 - 15:25.
 */

public class TimeUtils {

    /**
     * HH:mm
     *
     * @param time
     * @return
     */
    public static String formatTimeSimple(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.CHINA);
        return sdf.format(time);
    }


    /**
     * HH:mm:ss
     *
     * @param time
     * @return
     */
    public static String formatTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        return sdf.format(time);
    }

    /**
     * yyyy-MM-dd HH:mm
     *
     * @param time
     * @return
     */
    public static String formatDateAndTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return sdf.format(time);
    }

    /**
     * yyyy-MM-dd
     *
     * @param time
     * @return
     */
    public static String formatDate(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(time);
    }

    /**
     * format duration to HH:mm:ss
     *
     * @param duration
     * @return
     */
    public static String formatDuration(long duration) {
        int HOUR = (int) (duration / (1000 * 60 * 60));
        int MINUTE = (int) ((duration % (1000 * 60 * 60)) / (1000 * 60));
        int SECOND = (int) ((duration % (1000 * 60)) / 1000);

        if (HOUR == 0) {
            return String.format("%02d:%02d", MINUTE, SECOND);
        } else {
            return String.format("%02d:%02d:%02d", HOUR, MINUTE, SECOND);
        }

    }

}
