package me.r2d2.user;

import me.r2d2.commons.ErrorResponse;
import me.r2d2.util.BaseDto;
import me.r2d2.util.UrlDescription;
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
import java.io.UnsupportedEncodingException;

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
    @UrlDescription(name = "회원가입", description = "회원정보를 받아옵니다. 유효성검사 필수입니다")
    @RequestMapping(value = "/users", method = POST)
    public ResponseEntity createUser(@RequestBody @Valid UserDto.Create create, BindingResult result) throws Exception {

        if(result.hasErrors()){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("잘못된 요청입니다");
            errorResponse.setCode("bad.request");
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }

        User newUser = service.createUser(create);
        return new ResponseEntity(mapper.map(newUser, UserDto.CreateResponse.class), HttpStatus.CREATED);
    }

    /**
     * 유저 로그인
     * @param login
     * @param result
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/login", method = POST)
    public ResponseEntity loginUser(@RequestBody @Valid UserDto.Login login, BindingResult result) throws Exception {

        if(result.hasErrors()){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("잘못된 요청입니다");
            errorResponse.setCode("bad.request");
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }

        /** 유저가 존재하는지 체크 및 패스워드 일치 확인 */
        ResponseEntity responseEntity = service.loginUser(login);

        return responseEntity;
    }

    /**
     * 토큰 로그인
     * @param logon
     * @param result
     * @return
     */
    @RequestMapping(value = "/logon", method = POST)
    public ResponseEntity logonUser(@RequestBody @Valid UserDto.Logon logon, BindingResult result){

        if(result.hasErrors()){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("잘못된 요청입니다");
            errorResponse.setCode("bad.request");
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }

        User existUser = repository.findByUserId(logon.getUserId());

        /** 유저가 존재하면 */
        if(existUser != null){
            BaseDto.BaseResponse logonResponse = new BaseDto.BaseResponse("true", "userId.exist");
            return new ResponseEntity(logonResponse, HttpStatus.OK);
        }

        /** 유저가 존재하지 않으면 */
        BaseDto.BaseResponse logonResponse = new BaseDto.BaseResponse("false", "userId.Not.exist");

        return new ResponseEntity(logonResponse, HttpStatus.OK);
    }

    /**
     * 유저 중복 예외 처리
     * @param exception
     * @return
     */
    @ExceptionHandler(UserDuplicatedException.class)
    public ResponseEntity userDuplicatedExceptionHandler(UserDuplicatedException exception){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(exception.getEmail()+ " 는 중복된 이메일 입니다");
        errorResponse.setCode("duplicated.username.exception");
        //TODO : Bad request를 줘야하지만 Retrofit이 2.0부터. Bad Request를 받으려면 따로 인터페이스를 만들어줘야해서 잠시 보류
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    /**
     * 찾는 유저가 없을 때 예외 처리
     * @param exception
     * @return
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity userNotFoundExceptionHandler(UserNotFoundException exception){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(exception.getEmail()+ " 는 존재하지 않는 이메일 입니다");
        errorResponse.setCode("notfound.username.exception");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * 유저는 있으나 비밀번호가 일치하지 않을 때 예외 처리
     * @param exception
     * @return
     */
    @ExceptionHandler(PasswordWrongException.class)
    public ResponseEntity PasswordWrongExceptionHandler(PasswordWrongException exception){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("비밀번호가 틀렸습니다");
        errorResponse.setCode("wrong.password.exception");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }

}
