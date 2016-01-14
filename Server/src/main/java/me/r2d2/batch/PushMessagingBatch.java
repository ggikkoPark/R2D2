package me.r2d2.batch;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import me.r2d2.restaurant.RestaurantRepository;
import me.r2d2.user.User;
import me.r2d2.user.UserRepository;
import me.r2d2.util.Encryption2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Random;


/**
 * Created by Park Ji Hong, ggikko.
 */

@Service
public class PushMessagingBatch {

    private static String GCM_SERVER_KEY = "AIzaSyA5xch2jr5u3xysjjyb6jfqHCTL0U3SvRk";

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    public void test() throws IOException {

        String message = "world";
        String title = "hello";

        System.out.printf("test");

        List<User> all = userRepository.findAll();
        final Message.Builder messagebuilder = new Message.Builder();

        Encryption2 encryption = new Encryption2();

        for(User user : all){

            /** device 정보 복호화 */
            String tempDeviceId = user.getDeviceId();
            String deviceId = encryption.base64Decode(tempDeviceId);

            messagebuilder.addData("message", URLEncoder.encode(message, "UTF-8"));
            messagebuilder.addData("title", URLEncoder.encode(title, "UTF-8"));
            Sender sender =new Sender(GCM_SERVER_KEY);
            Result result = sender.send(messagebuilder.build(),deviceId, 5);

        }
    }

    private int[] getRandom(List list, int lastSize) {

        /** 리스트의 사이즈 개수 */
        int size = list.size();

        /** 인트형 배열 선언 */
        int a[] = new int[lastSize];

        /** 랜덤 객체 생성 */
        Random random = new Random();

        /** 원하는 개수 만큼 뽑기 위한 for문 */
        for (int i = 0; i < lastSize; i++) {
            /** 0 ~ 원하는 크기 만큼의 숫자를 뽑음 */
            a[i] = random.nextInt(size);

            /** 중복제거를 위한 for문 */
            for (int j = 0; j < i; j++) {

                /** 현재 a[]에 저장된 랜덤숫자와 이전에 a[]에 들어간 숫자 비교 */
                if (a[i] == a[j]) {
                    i--;
                }
            }
        }
        return a;
    }
}
