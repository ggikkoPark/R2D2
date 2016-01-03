package ggikko.me.r2d2.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ggikko on 15. 12. 28..
 */

/**
 * 유저 데이터 전송 객체
 */
public class UserDto {

    @Data
    @AllArgsConstructor
    public static class Create {

        private String email;
        private String password;
        private String subwayNumber;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class LogonResponse{
        private String email;
        private String subwaynumber;
        private String token;
        private String status;
        private String message;
    }

    @Data
    @AllArgsConstructor
    public static class JoinResponse{
        private String userId;
        private String code;
        private String message;
    }

    /**
     *  token 로그인
     */
    @Data
    @AllArgsConstructor
    public static class Logon{
        private String userId;
    }

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
