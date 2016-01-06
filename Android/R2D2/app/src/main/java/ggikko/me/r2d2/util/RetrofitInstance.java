package ggikko.me.r2d2.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ggikko.me.r2d2.deserializer.LoginDeserializer;
import ggikko.me.r2d2.deserializer.LogonDeserializer;
import ggikko.me.r2d2.deserializer.RestaurantsDeserializer;
import ggikko.me.r2d2.deserializer.UserDeserializer;
import ggikko.me.r2d2.domain.RestaurantDto;
import ggikko.me.r2d2.domain.UserDto;
import ggikko.me.r2d2.user.UrlInformation;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by ggikko on 16. 1. 3..
 */
public class RetrofitInstance {

    /** RetrofitInstance 인스턴스 */
    private static RetrofitInstance instance;

    /** 싱글톤 패턴을 이용하여 인스턴스 생성 */
    public static RetrofitInstance getInstance(){
        if (instance == null) {
            instance = new RetrofitInstance();
        }
        return instance;
    }

    /** 회원가입 할 때 사용하는 retrofit */
    public Retrofit getJoinRetrofit() {

        Gson gson = new GsonBuilder().registerTypeAdapter(UserDto.Create.class, new UserDeserializer()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlInformation.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

    /** 로그인 할 때 사용하는 retrofit */
    public Retrofit getLoginRetrofit() {

        Gson gson = new GsonBuilder().registerTypeAdapter(UserDto.Login.class, new LoginDeserializer()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlInformation.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

    /** 자동로그인 할 때 사용하는 retrofit */
    public Retrofit getLogonRetrofit() {

        Gson gson = new GsonBuilder().registerTypeAdapter(UserDto.Logon.class, new LogonDeserializer()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlInformation.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

    /** 맛집 리스트를 받아올 때 사용하는 retrofit */
    public Retrofit getRestaurantList() {

        Gson gson = new GsonBuilder().registerTypeAdapter(RestaurantDto.GetRestaurants.class, new RestaurantsDeserializer()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlInformation.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

}
