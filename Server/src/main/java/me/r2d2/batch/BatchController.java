package me.r2d2.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by Park Ji Hong, ggikko.
 */

@RestController
public class BatchController {

    @Autowired
    private PushMessaging pushMessaging;

    @Autowired
    private BlogDataProcessing blogDataProcessing;

    /**
     * GCM API
     * @throws IOException
     */
    @RequestMapping(value = "gcm", method = GET)
    public void gcmTest() throws IOException {
        pushMessaging.test();
    }

    /**
     * blogData API
     * @throws IOException
     */
    @RequestMapping(value = "blog", method = GET)
    public void getBlogData() throws IOException {
        blogDataProcessing.getDataAndDataProcessing("강남+회사+점심");
    }

}
