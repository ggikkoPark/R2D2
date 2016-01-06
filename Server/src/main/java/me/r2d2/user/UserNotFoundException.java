package me.r2d2.user;

import lombok.Getter;

/**
 * Created by Park Ji Hong, ggikko.
 */
@Getter
public class UserNotFoundException extends RuntimeException {

    private String email;

    public UserNotFoundException(String email) {
        this.email = email;
    }
}
