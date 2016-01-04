package me.r2d2.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    private String userId;

    /** 사용자 이메일 PK */
    private String email;

    /** 사용자 비밀번호 */
    private String password;

    /** 디바이스 정보 */
    private String deviceId;

    /** 지역 번호
     * 각 지하철 역마다 임의로 지정된 번호
     * */
    private String subwayNumber;

    /** 카테고리
     * 사용자가 선택할 수 있는 범주
     * */
    private String category;

    /** 등록일 */
    @Temporal(TemporalType.TIMESTAMP)
    private Date joined;

    /** 수정일 */
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;


}
