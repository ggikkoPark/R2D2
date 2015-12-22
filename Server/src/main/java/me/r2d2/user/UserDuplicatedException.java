package me.r2d2.user;

import lombok.Getter;

/**
 * Created by Park Ji Hong, ggikko.
 */

/**
 * 유저 중복 예외 처리 클래스
 */
@Getter
public class UserDuplicatedException extends RuntimeException {

    private String email;

    public UserDuplicatedException(String email) {
        this.email = email;
    }
}
