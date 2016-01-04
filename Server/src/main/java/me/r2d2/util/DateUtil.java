package me.r2d2.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Park Ji Hong, ggikko.
 */
public class DateUtil {


    /** 디폴트 패턴 */
    private final static String defaultPattern = "yyyyMMdd";

    /**
     * 오늘 일자 가져옴
     * @return yyyyMMdd
     */
    public static String getToday(){
        return getToday(defaultPattern);
    }

    /**
     * 오늘 일자 가져옴
     * @param pattern 패턴
     * @return 입력한 패턴 형식의 오늘일자
     */
    public static String getToday(String pattern){

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar c1 = Calendar.getInstance();

        return sdf.format(c1.getTime());
    }
}
