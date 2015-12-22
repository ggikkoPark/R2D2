package me.r2d2.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import me.r2d2.Application;
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

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by Park Ji Hong, ggikko.
 */

/**
 * 유저 컨트롤러 테스트 케이스
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class UserControllerTest extends TestCase {

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
     * 유저 생성 테스트
     * @throws Exception
     */
    @Test
    public void createUser() throws Exception {

        UserDto.Create createDto = new UserDto.Create();
        createDto.setEmail("ggikko@gmail.com");
        createDto.setPassword("12345");
        createDto.setLocalNumber("1");

        ResultActions result = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createDto)));

        result.andDo(print());
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.email", is("ggikko@gmail.com")));
        result.andExpect(jsonPath("$.localNumber", is("1")));

        result = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createDto)));

        result.andDo(print());
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.code", is("duplicated.username.exception")));

    }

    /**
     * 유저 생성 Bad Request 테스트
     * @throws Exception
     */
    @Test
    public void createUser_BadRequest() throws Exception {
        UserDto.Create createDto = new UserDto.Create();
        createDto.setEmail("ggikko@gmail.com");
        createDto.setPassword(" ");
        createDto.setLocalNumber("1");

        ResultActions result = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createDto)));

        result.andDo(print());
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.code", is("bad.request")));

    }


}