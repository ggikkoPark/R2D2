package me.r2d2.restaurant;

import me.r2d2.user.User;
import me.r2d2.user.UserRepository;
import me.r2d2.util.BaseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by Park Ji Hong, ggikko.
 */

@Service
public class RestaurantService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ModelMapper mapper;

    public ResponseEntity getRestaurants(RestaurantDto.GetRestaurants getRestaurants) {

        User existUser = userRepository.findByUserId(getRestaurants.getUserId());

        /** 유저가 존재하지 않으면 */
        if (existUser != null) {
            BaseDto.BaseResponse logonResponse = new BaseDto.BaseResponse("false", "userId.Not.exist");
            return new ResponseEntity(logonResponse, HttpStatus.OK);
        }

        Restaurant restaurants = restaurantRepository.findBySubwayNumber(getRestaurants.getSubwayNumber());


        return new ResponseEntity(mapper.map(restaurants, RestaurantDto.Restaurants.class), HttpStatus.OK);
    }

    public void saveTestObject(){

        Restaurant test = new Restaurant();
        test.setSubwayNumber("1");
        test.setRestaurant1("맥도날드");
        test.setRestaurant2("롯데리아");
        test.setRestaurant3("치킨");
        test.setRestaurant4("롯데월드");
        test.setRestaurant5("니달리");
        test.setRestaurant6("코르키");
        test.setRestaurant7("프로도");
        test.setRestaurant8("케릭터");
        test.setRestaurant9("헤헤");
        test.setRestaurant10("하하");
        restaurantRepository.save(test);

    }
}
