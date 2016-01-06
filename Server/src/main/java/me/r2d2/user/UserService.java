package me.r2d2.user;

import me.r2d2.util.Encryption;
import me.r2d2.util.RandomUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;

/**
 * Created by Park Ji Hong, ggikko.
 */

/**
 * 유저 서비스 for Business logic.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * 데이터베이스에 User정보를 저장한다
     * @param dto
     * @return
     */
    public User createUser(UserDto.Create dto) {

        User user = modelMapper.map(dto, User.class);
        String email = dto.getEmail();
        String password = dto.getPassword();
        String deviceId = dto.getDeviceId();

        Encryption encryption = new Encryption();

        // TODO : Bcrypt 암호화 + Spring security
        /** device Id 임시 암호화 */
        if(deviceId != null) {
            deviceId = encryption.base64Encode(deviceId);
            user.setDeviceId(deviceId);
        }

        /** device Id 임시 암호화 */
        password = encryption.base64Encode(password);

        /** 유저가 존재할 경우 예외처리 */
        if(repository.findByEmail(email) != null){
            throw new UserDuplicatedException(email);
        }

        user.setPassword(password);
        user.setUserId(RandomUtil.idGenerator());

        /** 가입 날짜, 업데이트 날짜 삽입 */
        Date now = new Date();
        user.setJoined(now);
        user.setUpdated(now);

        return repository.save(user);
    }

    public ResponseEntity loginUser(UserDto.Login dto) throws UnsupportedEncodingException {

        Encryption encryption = new Encryption();

        String email = dto.getEmail();
        String password = dto.getPassword();
        User user = repository.findByEmail(email);

        /** 이메일이 존재하지 않으면 */
        if(user == null){
            throw new UserNotFoundException(email);
        }else {
            /** 이메일이 존재하면 */
            String existUserPassword = user.getPassword();
            /** 패스워드가 일치하지 않으면 */
            if(!password.equals(encryption.base64Decode(existUserPassword))){
                throw new PasswordWrongException();
            }
        }

        /** 패스워드가 일치하면 */
        return new ResponseEntity(HttpStatus.OK);
    }
}
