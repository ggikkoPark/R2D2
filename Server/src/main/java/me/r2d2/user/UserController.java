package me.r2d2.user;

import me.r2d2.commons.ErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Park Ji Hong, ggikko.
 */

/**
 * 유저 Controller
 */
@RestController
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper mapper;

    /**
     * 유저 생성
     * @param create
     * @param result
     * @return
     */
    @RequestMapping(value = "/users", method = POST)
    public ResponseEntity createUser(@RequestBody @Valid UserDto.Create create, BindingResult result){

        if(result.hasErrors()){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("잘못된 요청입니다");
            errorResponse.setCode("bad.request");
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }

        User newUser = service.createUser(create);

        return new ResponseEntity(mapper.map(newUser, UserDto.Create.class), HttpStatus.CREATED);
    }

    /**
     * 유저 중복 예외 처리
     * @param exception
     * @return
     */
    @ExceptionHandler(UserDuplicatedException.class)
    public ResponseEntity userDuplicatedExceptionHandler(UserDuplicatedException exception){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("[" + exception.getEmail()+ "] 중복된 userName 입니다");
        errorResponse.setCode("duplicated.username.exception");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }



}
