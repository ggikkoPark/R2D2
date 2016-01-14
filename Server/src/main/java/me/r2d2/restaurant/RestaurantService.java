package me.r2d2.restaurant;

import me.r2d2.user.User;
import me.r2d2.user.UserRepository;
import me.r2d2.util.BaseDto;
import me.r2d2.data.SubwayInformation;
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
        if (existUser == null) {
            BaseDto.BaseResponse logonResponse = new BaseDto.BaseResponse("false", "userId.Not.exist");
            return new ResponseEntity(logonResponse, HttpStatus.OK);
        }

        String subwayNumber = getRestaurants.getSubwayNumber();

        /** 임시 역 설정 */
        if(subwayNumber.equals("gangnam"))subwayNumber= SubwayInformation.GANGNAM;
        if(subwayNumber.equals("yeoksam"))subwayNumber = SubwayInformation.YEOKSAM;
        if(subwayNumber.equals("seolleung"))subwayNumber = SubwayInformation.SEOLLEUNG;

        /** 맛집 정보를 가져온다 */
        Restaurant restaurants = restaurantRepository.findOne(subwayNumber);

        /** 해당하는 역이 있으면 반환 */
        if(restaurants !=null) return new ResponseEntity(mapper.map(restaurants, RestaurantDto.Restaurants.class), HttpStatus.OK);

        /** 해당하는 역이 없으면 Not Found Status */
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
