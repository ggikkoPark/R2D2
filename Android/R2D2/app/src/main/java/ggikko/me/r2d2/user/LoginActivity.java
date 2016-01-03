package ggikko.me.r2d2.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import ggikko.me.r2d2.R;
import ggikko.me.r2d2.api.user.UserAPI;
import ggikko.me.r2d2.domain.UserDto;
import ggikko.me.r2d2.home.HomeActivity;
import ggikko.me.r2d2.util.RetrofitInstance;
import ggikko.me.r2d2.util.SharedInformation;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 로그인 화면 Activity
 */
public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(v -> goToHomeActivity());

        Button btn_goto_join = (Button) findViewById(R.id.btn_goto_join);
        btn_goto_join.setOnClickListener(v -> goToJoinActivity());
    }

    /**
     * 다음 화면 페이지 넘어간다(홈 화면)
     */
    private void goToHomeActivity() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    /**
     * 다음 화면 페이지 넘어간다(회원가입 화면)
     */
    private void goToJoinActivity() {
        Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
        startActivity(intent);
    }

    /**
     * 로그인 중이면 페이지 자동으로 넘김
     * request - UserDto.
     * response - UserDto.JoinResponse
     */
    @Override
    protected void onResume() {
        super.onResume();
        SharedInformation sharedInformation = SharedInformation.getInstance();
        String token = sharedInformation.getToken(LoginActivity.this);

        if (!token.equals("R2D2")) {

            /** 로그인에 사용할 retrofit instance 얻어옴 */
            RetrofitInstance retrofitInstance = RetrofitInstance.getInstance();
            Retrofit retrofit = retrofitInstance.getLogonRetrofit();

            /** 다이얼로그 생성 */
            final ProgressDialog pDialog = new ProgressDialog(this);
            pDialog.setMessage("잠시만 기다려주세요.");
            pDialog.show();

            UserDto.Logon logon = new UserDto.Logon(token);
            UserAPI userAPI = retrofit.create(UserAPI.class);
            Call<UserDto.BaseResponse> logonCall = userAPI.reqLogon(logon);

            /** retrofit 콜백 메소드 성공시 onResponse, 실패시 onFailure */
            logonCall.enqueue(new Callback<UserDto.BaseResponse>() {
                @Override
                public void onResponse(Response<UserDto.BaseResponse> response, Retrofit retrofit) {
                    UserDto.BaseResponse body = response.body();
                    Log.e("ggikko", "ok");
                    if (body != null) {
                        pDialog.hide();
                        String code = body.getCode();
                        if (code != null) {
                            if (code.equals("true")) {
                                goToHomeActivity();
                                finish();
                            } else {
                                Log.e("ggikko", "not");
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }

}
