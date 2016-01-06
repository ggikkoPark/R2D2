package me.r2d2.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by Park Ji Hong, ggikko.
 */

/**
 * 유저 데이터 전송 객체
 */
public class UserDto {

    /** user 생성
     * email : 유저의 이메일 / Not Blank, Size min = 5
     * password : 유저의 패스워드 / Not Blank, Size min = 5
     * subwayNumber : 유저가 원하는 역 번호 / Not Blank
     */
    @Data
    public static class Create{

        @NotBlank
        @Size(min =5)
        private String email;

        @NotBlank
        @Size(min =5)
        private String password;

        private String deviceId;

        @NotBlank
        private String subwayNumber;

    }

    /** user 로그인
     * email : 유저의 이메일 / Not Blank, Size min = 5
     * password : 유저의 패스워드 / Not Blank, Size min = 5
     * subwayNumber : 유저가 원하는 역 번호 / Not Blank
     */
    @Data
    public static class Login{

        @NotBlank
        @Size(min =5)
        private String email;

        @NotBlank
        @Size(min =5)
        private String password;

    }


    /** user 생성 요청에 대한 응답 */
    @Data
    public static class CreateResponse{

        private String userId;
        private String subwayNumber;
        private String code;
        private String message;


    }

    /** user 정보 요청에 대한 응답 */
    @Data
    public static class Response{

        private String email;
        private String subwayNumber;

    }

    /**
     *  token 로그인
     */
    @Data
    public static class Logon{

        @NotBlank
        private String userId;

    }




}
