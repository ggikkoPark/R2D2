package ggikko.me.r2d2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import ggikko.me.r2d2.api.user.UserAPI;
import ggikko.me.r2d2.domain.BaseDto;
import ggikko.me.r2d2.domain.UserDto;
import ggikko.me.r2d2.home.HomeActivity;
import ggikko.me.r2d2.user.LoginActivity;
import ggikko.me.r2d2.util.RetrofitInstance;
import ggikko.me.r2d2.util.SharedInformation;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 처음 애플리케이션 진입점
 * 애니메이션을 3초동안 보여주고 Home Activity 로 전환시켜준다.
 * 보여주는 동안 로그인이 되어있는지 체크한다
 */
public class SplashActivity extends AppCompatActivity {

    private ImageView iv_splash;

    private AnimationDrawable animation;

    private Thread thread;

    private String subwayNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        iv_splash = (ImageView) findViewById(R.id.iv_splash);
        //TODO 애니메이션을 넣었으나 이쁘지 않아서 잠시 빼두었습니다.
//        animation = (AnimationDrawable) iv_splash.getBackground();


        threadSetting();

        thread.start();

    }

    private void checkLogon() {
        SharedInformation sharedInformation = SharedInformation.getInstance();
        String token = sharedInformation.getToken(SplashActivity.this);
        subwayNumber = sharedInformation.getSubwayNumber(SplashActivity.this);


        if (!token.equals("R2D2")) {

            /** 로그인에 사용할 retrofit instance 얻어옴 */
            RetrofitInstance retrofitInstance = RetrofitInstance.getInstance();
            Retrofit retrofit = retrofitInstance.getLogonRetrofit();

            UserDto.Logon logon = new UserDto.Logon(token);
            UserAPI userAPI = retrofit.create(UserAPI.class);
            Call<BaseDto.BaseResponse> logonCall = userAPI.reqLogon(logon);

            /** retrofit 콜백 메소드 성공시 onResponse, 실패시 onFailure */
            logonCall.enqueue(new Callback<BaseDto.BaseResponse>() {

                @Override
                public void onResponse(Response<BaseDto.BaseResponse> response, Retrofit retrofit) {
                    BaseDto.BaseResponse body = response.body();
                    Log.e("ggikko", "ok");
                    if (body != null) {
                        String code = body.getCode();
                        if (code != null) {
                            if (code.equals("true")) {
                                goToHomeActivity();
                                finish();
                            }else{
                                goToLoginActivity();
                            }
                        }
                    }else{
                        goToLoginActivity();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    goToLoginActivity();
                }
            });
        }else{
            goToLoginActivity();
        }
    }

    /**
     * 다음 화면 페이지 넘어간다(홈 화면)
     */
    private void goToHomeActivity() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        intent.putExtra("subwayNumber", subwayNumber);
        startActivity(intent);
        finish();
    }

    /**
     * 다음 화면 페이지 넘어간다(로그인 화면)
     */
    private void goToLoginActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    /**
     * 새로운 쓰레드를 만들어서 이미지를 약 3초간 보여주도록 설정
     */
    private void threadSetting() {
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    Thread.sleep(2000);

//                    goToLoginActivity();
                    /** 유저가 로그온 상태인지 체크합니다 */
                    checkLogon();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Splash Activity 애니메이션을 동작하도록 한다.
     */
    private void startAnimation() {
        iv_splash.post(new Runnable() {
            @Override
            public void run() {
                animation.start();
            }
        });
    }

    /**
     * Splash Activity 애니메이션을 멈추도록 한다.
     */
    private void stopAnimation() {
        animation.stop();
    }

    /**
     * 다시 페이지가 Resume 되었을 때 애니메이션을 동작시킵니다
     */
    @Override
    protected void onResume() {
        super.onResume();
//        startAnimation();
    }

    /**
     * 페이지기 pause 되었을 때 애니메이션 동작을 멈춥니다.
     */
    @Override
    protected void onPause() {
        super.onPause();
//        stopAnimation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (animation != null) stopAnimation();
    }
}
