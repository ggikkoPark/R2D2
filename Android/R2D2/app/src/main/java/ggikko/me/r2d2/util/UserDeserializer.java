package ggikko.me.r2d2.util;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import ggikko.me.r2d2.domain.UserDto;

/**
 * Created by ggikko on 16. 1. 3..
 */

/**
 * Json 객체를 받아서 클래스 타입으로 맵핑해주는 클래스
 * 객체 타입으로 역 직렬화
 */
public class UserDeserializer implements JsonDeserializer<Object> {

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonElement user = json.getAsJsonObject();

        return (new Gson().fromJson(user, UserDto.JoinResponse.class));
    }

}