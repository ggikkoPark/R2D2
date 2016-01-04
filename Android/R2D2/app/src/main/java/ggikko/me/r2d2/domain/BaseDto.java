package ggikko.me.r2d2.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by ggikko on 16. 1. 4..
 */
public class BaseDto {

    /**
     *  서버로 부터 받는 기본 응답
     *  code, message
     */
    @Data
    @AllArgsConstructor
    public static class BaseResponse {

        private String message;
        private String code;

    }
}
