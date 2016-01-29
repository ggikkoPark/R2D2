package me.r2d2.user;

/**
 * Created by Park Ji Hong, ggikko.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import me.r2d2.Application;
import me.r2d2.restaurant.RestaurantDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 맛집 컨트롤러 테스트 케이스
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class RestaurantControllerTest extends TestCase{

    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper mapper;

    MockMvc mockMvc;

    /**
     * web application context를 setup하여 목객체 build
     */
    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * Restaurant List를 가져오는 테스트
     * @throws Exception
     */
    @Test
    public void getRestaurants() throws Exception {
        RestaurantDto.GetRestaurants getRestaurants = new RestaurantDto.GetRestaurants();
        getRestaurants.setSubwayNumber("1");
        getRestaurants.setUserId("20160104-AwZ82KUt7H");


        ResultActions result = mockMvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(getRestaurants)));

        result.andDo(print());
        result.andExpect(status().isOk());
    }


}
