package me.r2d2.restaurant;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Park Ji Hong, ggikko.
 */
public class RestaurantDto {

    @Data
    public static class GetRestaurants{

        @NotBlank
        private String subwayNumber;

        @NotBlank
        private String userId;

    }

    @Data
    public static class Restaurants{

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

}
