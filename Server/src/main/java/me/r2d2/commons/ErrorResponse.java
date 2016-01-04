package me.r2d2.commons;

import lombok.Data;

import java.util.List;

/**
 * Created by Park Ji Hong, ggikko.
 */

/**
 * 에러 응답 데이터
 * 에러 메세지, 코드
 */
@Data
public class ErrorResponse {

    private String code;
    private String message;

}
