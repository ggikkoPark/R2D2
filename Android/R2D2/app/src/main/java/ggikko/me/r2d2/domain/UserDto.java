package ggikko.me.r2d2.domain;

import lombok.Data;

/**
 * Created by ggikko on 15. 12. 28..
 */

/**
 * 유저 데이터 전송 객체
 */
public class UserDto {

    @Data
    public static class Create {

        private String email;
        private String password;
        private String subwayNumber;

    }


    @Data
    public static class Response{

        private String email;
        private String subwayNumber;
        private String token;

    }

}
