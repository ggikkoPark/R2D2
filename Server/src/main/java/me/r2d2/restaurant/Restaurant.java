package me.r2d2.restaurant;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Park Ji Hong, ggikko.
 */

@Entity
@Getter
@Setter
public class Restaurant {

    @Id
    private String subwayNumber;

    /** 맛집 1 */
    private String restaurant1;
    /** 맛집 2 */
    private String restaurant2;
    /** 맛집 3 */
    private String restaurant3;
    /** 맛집 4 */
    private String restaurant4;
    /** 맛집 5 */
    private String restaurant5;
    /** 맛집 6 */
    private String restaurant6;
    /** 맛집 7 */
    private String restaurant7;
    /** 맛집 8 */
    private String restaurant8;
    /** 맛집 9 */
    private String restaurant9;
    /** 맛집 10 */
    private String restaurant10;

}
