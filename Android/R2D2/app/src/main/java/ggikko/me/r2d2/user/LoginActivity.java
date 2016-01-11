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

    private String subwayNumber;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_login = (Button) findViewById(R.id.btn_login);
        Button btn_goto_findpwd = (Button) findViewById(R.id.btn_goto_findpwd);

        btn_login.setOnClickListener(v -> goToHomeActivity());

        Button btn_goto_join = (Button) findViewById(R.id.btn_goto_join);
        btn_goto_join.setOnClickListener(v -> goToJoinActivity());
        btn_goto_findpwd.setOnClickListener(v -> goToFindPwdActivity());
    }


    /**
     * 다음 화면 페이지 넘어간다(비밀번호 찾기 화면)
     */
    private void goToFindPwdActivity() {
        Intent intent = new Intent(LoginActivity.this, FindPwdActivity.class);
        startActivity(intent);
    }

    /**
     * 다음 화면 페이지 넘어간다(홈 화면)
     */
    private void goToHomeActivity() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("subwayNumber",subwayNumber);
        startActivity(intent);
        finish();
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
        token = sharedInformation.getToken(LoginActivity.this);
        subwayNumber = sharedInformation.getSubwayNumber(LoginActivity.this);


    }

}
