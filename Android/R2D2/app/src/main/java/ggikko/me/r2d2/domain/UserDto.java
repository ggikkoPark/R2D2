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

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Create {

        private String email;
        private String password;
        private String subwayNumber;

    }


    @Getter
    @Setter
    @AllArgsConstructor
    public static class JoinResponse{

        private String email;
        private String subwaynumber;
        private String token;
        private String status;
        private String message;

    }

}
