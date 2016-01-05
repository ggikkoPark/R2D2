package ggikko.me.r2d2.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by ggikko on 16. 1. 3..
 */

/**
 * SharedPreference를 이용하여 토큰 저장
 */
public class SharedInformation {

    /** SharedInformation 인스턴스 */
    private static SharedInformation instance;

    /** 토큰 저장 파일명 */
    String TOKEN_FILE_NAME = "TOKENSTORE";

    /** 지하철 역 저장 파일명 */
    String SUBWAYNUMBER_FILE_NAME = "SUBWAYNUMBERSTORE";

    /** 토큰 찾아오는 키 */
    String TOKEN_KEY = "TOKEN";

    /** 지하철역 찾아오는 키 */
    String SUBWAYNUMBER_KEY = "SUBWAYNUMBER";

    public static final String DEFAULT = "R2D2";

    /** 싱글톤 패턴을 이용하여 인스턴스 생성 */
    public static SharedInformation getInstance(){
        if (instance == null) {
            instance = new SharedInformation();
        }
        return instance;
    }

    /** 토큰 저장 */
    public void saveToken(Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TOKEN_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.commit();
    }
    /** 지하철 역 저장 */
    public void saveSubwayNumber(Context context, String subwayNumber) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SUBWAYNUMBER_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SUBWAYNUMBER_KEY, subwayNumber);
        editor.commit();
    }

    /** 토큰 획득 */
    public String getToken(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(TOKEN_FILE_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN_KEY, DEFAULT);
        if(token != null){
            return token;
        }
        return DEFAULT;
    }

    /** 지하철역 획득 */
    public String getSubwayNumber(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SUBWAYNUMBER_FILE_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(SUBWAYNUMBER_KEY, DEFAULT);
        if(token != null){
            return token;
        }
        return DEFAULT;
    }


}
