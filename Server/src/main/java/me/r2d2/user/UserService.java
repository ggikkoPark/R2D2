package me.r2d2.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Park Ji Hong, ggikko.
 */

/**
 * 유저 서비스 for Business logic.
 */
@Service
@Transactional
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

        if(repository.findByEmail(email) != null){
            throw new UserDuplicatedException(email);
        }

        Date now = new Date();
        user.setJoined(now);
        user.setUpdated(now);

        return repository.save(user);
    }


}
