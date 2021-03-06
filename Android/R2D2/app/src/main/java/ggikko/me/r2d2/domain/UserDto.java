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

    /**
     * 회원가입 요청 객체
     */
    @Data
    @AllArgsConstructor
    public static class Create {
        private String email;
        private String password;
        private String subwayNumber;
        private String deviceId;
    }

    /**
     * 회원가입 응답 객체
     */
    @Data
    @AllArgsConstructor
    public static class JoinResponse{
        private String userId;
        private String subwayNumber;
        private String code;
        private String message;
    }

    /**
     *  token 로그인 요청 객체
     */
    @Data
    @AllArgsConstructor
    public static class Logon{
        private String userId;
    }

    /**
     * 로그온 응답 객체
     */
    @Getter
    @Setter
    @AllArgsConstructor
    public static class LogonResponse{
        private String email;
        private String subwayNumber;
        private String token;
        private String status;
        private String message;
    }

    /**
     * 로그인 요청 객체
     */
    @Data
    @AllArgsConstructor
    public static class Login{
        private String email;
        private String password;
        private String deviceId;
    }

    /**
     * 로그인 응답 객체
     */
    @Data
    @AllArgsConstructor
    public static class LoginResponse{
        private String userId;
        private String subwayNumber;
    }

    /**
     * 비밀번호 찾기 응답 객체
     */
    @Data
    @AllArgsConstructor
    public static class FindPassword{
        private String userId;
        private String subwayNumber;
        private String code;
        private String message;
    }

    /**
     * 비밀번호 찾기 응답 객체
     */
    @Data
    @AllArgsConstructor
    public static class FindPasswordResponse{
        private String code;
        private String message;
    }


}
