package me.r2d2.util;

import java.util.Random;

/**
 * Created by Park Ji Hong, ggikko.
 */

public class RandomUtil {

    /**
     * 대소문자 + 숫자 랜덤 문자열 리턴
     * @param 문자열 갯수
     * @return 랜덤 문자열
     */
    public static String randomString(int n){
        Random ran = new Random();
        String value = "";
        int i;

        for (i = 0; i < n; i++) {     //  원하는 난수의 길이
            int num1 = (int) 48 + (int) (ran.nextDouble() * 74);
            value = value + (char) num1;
        }

        return value;
    }

    /**
     * 아이디 생성
     * @return
     */
    public static String idGenerator(){

        String id = DateUtil.getToday()+"-"+randomString(10);

        return id;
    }

}

