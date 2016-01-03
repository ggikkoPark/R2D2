package ggikko.me.r2d2.api.user;

import ggikko.me.r2d2.domain.UserDto;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by ggikko on 15. 12. 24..
 */
public interface UserAPI {

    /**
     * 가입 API 인터페이스
     * @param createUser
     * @return
     */
    @POST("users")
    Call<UserDto.JoinResponse> createUser(@Body UserDto.Create createUser);

    /**
     * 로그온 요청 API 인터페이스
     * @param reqLogon
     * @return
     */
    @POST("logon")
    Call<UserDto.BaseResponse> reqLogon(@Body UserDto.Logon reqLogon);



}
