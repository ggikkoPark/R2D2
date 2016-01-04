package me.r2d2.restaurant;

import me.r2d2.commons.ErrorResponse;
import me.r2d2.user.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Park Ji Hong, ggikko.
 */

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService service;

    @RequestMapping(value = "/restaurants", method = POST)
    public ResponseEntity getRestaurants(@RequestBody @Valid RestaurantDto.GetRestaurants getRestaurants, BindingResult result){

        if(result.hasErrors()){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("잘못된 요청입니다");
            errorResponse.setCode("bad.request");
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }

        /** 가짜데이터 한개 생성 */
        service.saveTestObject();

        ResponseEntity responseEntity = service.getRestaurants(getRestaurants);
        return responseEntity;
    }

}
