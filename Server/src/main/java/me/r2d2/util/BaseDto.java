package me.r2d2.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Park Ji Hong, ggikko.
 */
public class BaseDto {

    /**
     *  token 로그인
     */
    @Data
    @AllArgsConstructor
    public static class BaseResponse{

        private String code;
        private String message;

    }
}

