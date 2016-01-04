package me.r2d2.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Park Ji Hong, ggikko.
 */

/**
 * 유저 Entity
 */
@Entity
@Getter
@Setter
public class User {

    @Id
    @Column(name = "user_id")
    private String userId;

    /** 사용자 이메일 PK */
    @Column(name = "email")
    private String email;

    /** 사용자 비밀번호 */
    @Column(name = "password")
    private String password;

    /** 디바이스 정보 */
    @Column(name = "device_id")
    private String deviceId;

    /** 지역 번호
     * 각 지하철 역마다 임의로 지정된 번호
     */
    @Column(name = "subway_number")
    private String subwayNumber;

    /** 카테고리
     * 사용자가 선택할 수 있는 범주
     * */
    @Column(name = "category")
    private String category;

    /** 등록일 */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "joined")
    private Date joined;

    /** 수정일 */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated")
    private Date updated;

}
