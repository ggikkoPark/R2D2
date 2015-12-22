package me.r2d2.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Park Ji Hong, ggikko.
 */

/**
 * 유저 정보 저장소
 */
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
}
