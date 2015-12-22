package me.r2d2.commons;

import lombok.Data;

import java.util.List;

/**
 * Created by Park Ji Hong, ggikko.
 */

/**
 * 에러 응답 데이터
 */
@Data
public class ErrorResponse {

    private String message;

    private String code;

    private List<FieldError> errors;

    public static class FieldError{
        private String field;
        private String value;
        private String reason;
    }

}
