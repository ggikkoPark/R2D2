package ggikko.me.r2d2.api.restaurant;

import ggikko.me.r2d2.domain.BaseDto;
import ggikko.me.r2d2.domain.RestaurantDto;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by ggikko on 16. 1. 4..
 */
public interface RestaurantAPI {


    /**
     * 맛집 요청 API 인터페이스
     * @param getRestaurants
     * @return
     */
    @POST("restaurants")
    Call<RestaurantDto.GetRestaurantsResponse> reqRestaurants(@Body RestaurantDto.GetRestaurants getRestaurants);

}
