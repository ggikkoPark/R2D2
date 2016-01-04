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

    /** 토큰 찾아오는 키 */
    String TOKEN_KEY = "TOKEN";

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

    /** 토큰 획득 */
    public String getToken(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(TOKEN_FILE_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN_KEY, DEFAULT);
        Log.e("ggikko", token + "   1");
        if(token != null){
            Log.e("ggikko", token + "   2");
            return token;
        }
        return DEFAULT;
    }


}
