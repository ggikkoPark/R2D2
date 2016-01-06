package ggikko.me.r2d2.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import ggikko.me.r2d2.domain.UserDto;

/**
 * Created by ggikko on 16. 1. 6..
 */
public class LoginDeserializer implements JsonDeserializer<Object> {

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonElement body = json.getAsJsonObject();

        return (new Gson().fromJson(body, UserDto.LoginResponse.class));
    }

}
