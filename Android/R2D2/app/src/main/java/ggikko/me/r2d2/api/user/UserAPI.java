package ggikko.me.r2d2.api.user;

import ggikko.me.r2d2.domain.BaseDto;
import ggikko.me.r2d2.domain.UserDto;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by ggikko on 15. 12. 24..
 */
public interface UserAPI {

    /**6
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
    Call<BaseDto.BaseResponse> reqLogon(@Body UserDto.Logon reqLogon);

    /**
     * 로그인 요청 API 인터페이스
     * @param reqLogin
     * @return
     */
    @POST("login")
    Call<UserDto.LoginResponse> reqLogin(@Body UserDto.Login reqLogin);

    /**
     * 비밀번호 찾기 요청 API 인터페이스
     * @param reqFindpassword
     * @return
     */
    @POST("users/findpassword")
    Call<UserDto.FindPasswordResponse> reqFindPassword(@Body UserDto.FindPassword reqFindpassword);

}
