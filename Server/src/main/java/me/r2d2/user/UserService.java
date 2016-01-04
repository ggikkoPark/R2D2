package me.r2d2.user;

import me.r2d2.util.Encryption;
import me.r2d2.util.RandomUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        /** device Id 임시 암호화 */
        Encryption encryption = new Encryption();

        if(dto.getDeviceId() != null) {
            String deviceId = encryption.base64Encode(dto.getDeviceId());
            System.out.printf(dto.getDeviceId());
            user.setDeviceId(deviceId);
            System.out.printf(deviceId);
        }

        /** 유저가 존재할 경우 예외처리 */
        if(repository.findByEmail(email) != null){
            throw new UserDuplicatedException(email);
        }

        user.setUserId(RandomUtil.idGenerator());

        /** 가입 날짜, 업데이트 날짜 삽입 */
        Date now = new Date();
        user.setJoined(now);
        user.setUpdated(now);

        return repository.save(user);
    }

}
