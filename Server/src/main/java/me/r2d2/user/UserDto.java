package me.r2d2.user;

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

    @Data
    public static class Create{

        @NotBlank
        @Size(min =5)
        private String email;

        @NotBlank
        @Size(min =5)
        private String password;

        @NotBlank
        private String subwayNumber;

    }

    @Data
    public static class Response{

        private String email;
        private String subwayNumber;

    }
}
