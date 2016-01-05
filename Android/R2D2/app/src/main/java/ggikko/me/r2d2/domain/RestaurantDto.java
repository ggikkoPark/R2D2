package ggikko.me.r2d2.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by ggikko on 16. 1. 4..
 */
public class RestaurantDto {

    @Data
    @AllArgsConstructor
    public static class GetRestaurants{
        private String subwayNumber;
        private String userId;
    }

    @Data
    public static class GetRestaurantsResponse{
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
