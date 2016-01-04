package me.r2d2.batch;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import me.r2d2.user.User;
import me.r2d2.user.UserRepository;
import me.r2d2.util.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Park Ji Hong, ggikko.
 */

@Service
public class BatchTest  {


    @Autowired
    UserRepository userRepository;

    public void test() throws IOException {

        String message = "world";
        String title = "hello";

        System.out.printf("test");

        List<User> all = userRepository.findAll();
        final Message.Builder messagebuilder = new Message.Builder();

        Encryption encryption = new Encryption();

        for(User user : all){

            /** device 정보 복호화 */
            String tempDeviceId = user.getDeviceId();
            String deviceId = encryption.base64Decode(tempDeviceId);

            messagebuilder.addData("message", URLEncoder.encode(message, "UTF-8"));
            messagebuilder.addData("title", URLEncoder.encode(title, "UTF-8"));
            Sender sender =new Sender("AIzaSyA5xch2jr5u3xysjjyb6jfqHCTL0U3SvRk");
            Result result = sender.send(messagebuilder.build(),deviceId, 5);

        }
    }
}
