package me.r2d2.user;

import me.r2d2.util.Encryption;
import me.r2d2.util.RandomUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    public User createUser(UserDto.Create dto) throws Exception {

        User user = modelMapper.map(dto, User.class);
        String email = dto.getEmail();
        String password = dto.getPassword();
        String deviceId = dto.getDeviceId();

        // TODO : Bcrypt 암호화 + Spring security
        /** device Id 임시 암호화 */
        if(deviceId != null) {
            deviceId = Encryption.seedEncode(deviceId);
            user.setDeviceId(deviceId);
        }

        /** device Id 임시 암호화 */
        password = Encryption.encryptOnly(password);

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

    public ResponseEntity loginUser(UserDto.Login dto) throws Exception {

        String email = dto.getEmail();
        String password = dto.getPassword();
        User user = repository.findByEmail(email);

        /** 이메일이 존재하지 않으면 */
        if(user == null){
            throw new UserNotFoundException(email);
        }else {
            /** 이메일이 존재하면 */
            String existUserPassword = user.getPassword();

            // TODO : Spring security
            /** 패스워드가 일치하지 않으면 */
            if(!password.equals(Encryption.seedDecode(existUserPassword))){
                throw new PasswordWrongException();
            }
        }

        String deviceId = dto.getDeviceId();
        if(deviceId!=null) {
            if (!user.getDeviceId().equals(Encryption.seedDecode(deviceId))) {
                user.setDeviceId(Encryption.seedEncode(deviceId));
                System.out.printf("ttttttt\n\n");
            }
        }else{
            System.out.printf("deviceIdNull\n\n");
        }

        User newUser = repository.save(user);
        System.out.printf("ttttttt\n\n");
        System.out.printf(newUser.getDeviceId());
        System.out.printf("ttttttt\n\n");

        /** 패스워드가 일치하면 */
        return new ResponseEntity(modelMapper.map(newUser, UserDto.LoginResponse.class), HttpStatus.OK);
    }
}
