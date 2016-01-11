package ggikko.me.r2d2.user;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.squareup.okhttp.ResponseBody;

import ggikko.me.r2d2.R;
import ggikko.me.r2d2.api.user.UserAPI;
import ggikko.me.r2d2.domain.UserDto;
import ggikko.me.r2d2.gcm.GcmPreferences;
import ggikko.me.r2d2.gcm.RegisterationIntentService;
import ggikko.me.r2d2.home.HomeActivity;
import ggikko.me.r2d2.util.JoinValidators;
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
    private String gcmToken;
    private BroadcastReceiver mGcm_BroadcastReceiver;

    private String email;
    private String password;


    Button btn_login, btn_goto_findpwd, btn_goto_join;
    EditText edit_login_email, edit_login_password;
    TextView txt_login_email, txt_login_password;

    private ProgressBar login_progressBar;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /** button 셋팅 */
        buttonSetting();

        /** Edit Text 셋팅 */
        edittextSetting();

        /** Text View 셋팅 */
        textViewSetting();

        /** progress bar 셋팅 */
        progressbarSetting();

        /** 브로드캐스트 리스버 등록 */
        setBroadcastReceiver();

    }

    private void progressbarSetting() {
        login_progressBar = (ProgressBar) findViewById(R.id.login_progressBar);
    }

    private void textViewSetting() {
        txt_login_email = (TextView) findViewById(R.id.txt_login_email);
        txt_login_password = (TextView) findViewById(R.id.txt_login_password);
    }

    private void edittextSetting() {
        edit_login_email = (EditText) findViewById(R.id.edit_login_email);
        edit_login_password = (EditText) findViewById(R.id.edit_login_password);
    }

    private void buttonSetting() {
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_goto_findpwd = (Button) findViewById(R.id.btn_goto_findpwd);
        btn_goto_join = (Button) findViewById(R.id.btn_goto_join);
        btn_goto_join.setOnClickListener(v -> goToJoinActivity());
        btn_goto_findpwd.setOnClickListener(v -> goToFindPwdActivity());
        btn_login.setOnClickListener(v -> requestLogin());
    }

    /**
     * 로그인 요청
     */
    private void requestLogin() {

        /** 유효성 검사를 위한 default setting */
        boolean emailIsOk = false;
        boolean pwdIsOk = false;

        /** 유효성 검사를 위한 email, password를 받아옴 */
        email = edit_login_email.getText().toString();
        password = edit_login_password.getText().toString();

        /** 이메일 & 패스워드 유효성 검사 */
        JoinValidators validators = new JoinValidators();
        emailIsOk = validators.checkEmail(email);
        pwdIsOk = validators.checkPassword(password);

        /** 유효성 검사를 통과하지 못하는 부분 명시 */
        if (!emailIsOk) txt_login_email.setVisibility(View.VISIBLE);
        if (!pwdIsOk) txt_login_password.setVisibility(View.VISIBLE);

        /** 유효성 검사 통과 후 로그인 */
        if (emailIsOk && pwdIsOk) {
            login_progressBar.setVisibility(View.VISIBLE);
            Log.e("ggikko", "test1");
            getInstanceIdToken();
        }

    }

    /**
     * 토큰을 얻어온다
     */
    public void getInstanceIdToken() {
        Log.e("ggikko", "test2");
        if (checkPlayServices()) {
            Log.e("ggikko", "test4");
            Intent intent = new Intent(LoginActivity.this, RegisterationIntentService.class);
            startService(intent);
        } else {
            //TODO : 구글 play service 가 불가능한 상황 분리
        }
    }

    /**
     * 구글 서비스 환경 체크
     */
    private boolean checkPlayServices() {
        Log.e("ggikko", "test3");
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(LoginActivity.this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, LoginActivity.this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
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
        intent.putExtra("subwayNumber", subwayNumber);
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
     * BroadcastReceiver 셋팅
     */
    private void setBroadcastReceiver() {

        mGcm_BroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                String action = intent.getAction();

                /** GcmToken 생성 준비중 */
                if (action.equals(GcmPreferences.READY)) {

                }

                /** GcmToken 생성중 */
                if (action.equals(GcmPreferences.GENERATING)) {

                }

                /** GcmToken 생성 완료 */
                if (action.equals(GcmPreferences.COMPLETE)) {
                    Log.e("ggikko", "test5");
                    gcmToken = intent.getStringExtra("token");
                    requestLoginToServer(email, password, gcmToken);
                }
            }
        };
    }

    /**
     * 서버에 로그인 요청을 한다
     */
    private void requestLoginToServer(String email, String password, String gcmToken) {

        /** 로그인 retrofit을 구해온다 */
        RetrofitInstance retrofitInstance = RetrofitInstance.getInstance();
        Retrofit retrofit = retrofitInstance.getLoginRetrofit();

        /** retrofit을 구체화 한다 */
        UserDto.Login loginUser = new UserDto.Login(email, password, gcmToken);
        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call<UserDto.LoginResponse> requestLoginCall = userAPI.reqLogin(loginUser);

        /** retrofit 콜백 메소드 성공시 onResponse, 실패시 onFailure */
        requestLoginCall.enqueue(new Callback<UserDto.LoginResponse>() {
            @Override
            public void onResponse(Response<UserDto.LoginResponse> response, Retrofit retrofit) {

                UserDto.LoginResponse body = response.body();

                Log.e("ggikko", "test6");

                Log.e("ggikko", "test7");

                if (response.isSuccess()) {
                    Log.e("ggikko", "test8");

                    Snackbar snackbar = Snackbar.make(btn_login, R.string.snack_login_success, Snackbar.LENGTH_LONG);
                    snackbar.show();

                    /** status code가 200, 201 - 성공시 토큰 저장 및 페이지 넘김 */
                    Log.e("ggikko", "status code : 200");
                    String userId = body.getUserId();
                    String subwayNumber = body.getSubwayNumber();
                    SharedInformation sharedInformation = SharedInformation.getInstance();
                    sharedInformation.saveToken(LoginActivity.this, userId);
                    sharedInformation.saveSubwayNumber(LoginActivity.this, subwayNumber);
                    login_progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("subwayNumber", subwayNumber);
                    finish();

                } else {
                    Log.e("ggikko", "test9");

                    /** status code가 400, 401, 403, etc */
                    /** 중복된 아이디 */
                    int statusCode = response.code();

                    login_progressBar.setVisibility(View.GONE);
                    if (statusCode == 404) {
                        /** 이메일을 찾지 못할 때 */
                        login_progressBar.setVisibility(View.GONE);
                        txt_login_email.setVisibility(View.VISIBLE);
                        txt_login_password.setVisibility(View.GONE);
                        Snackbar snackbar = Snackbar.make(btn_login, R.string.snack_login_notfound, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else if (statusCode == 406) {
                        /** 비밀번호가 틀렸을 때 */
                        login_progressBar.setVisibility(View.GONE);
                        txt_login_email.setVisibility(View.GONE);
                        txt_login_password.setVisibility(View.VISIBLE);
                        Snackbar snackbar = Snackbar.make(btn_login, R.string.snack_login_notacceptable, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }

                Log.e("ggikko", "test11");
                login_progressBar.setVisibility(View.GONE);

            }

            /** 서버 연동에 실패하였을 경우 */
            @Override
            public void onFailure(Throwable t) {
                Log.e("ggikko", "test10");
                login_progressBar.setVisibility(View.GONE);
                Snackbar snackbar = Snackbar.make(btn_login, R.string.snack_login_servererror, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });


    }


    /**
     * Resume 브로드 캐스트 리시버 등록
     */
    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(mGcm_BroadcastReceiver,
                new IntentFilter(GcmPreferences.READY));
        LocalBroadcastManager.getInstance(this).registerReceiver(mGcm_BroadcastReceiver,
                new IntentFilter(GcmPreferences.GENERATING));
        LocalBroadcastManager.getInstance(this).registerReceiver(mGcm_BroadcastReceiver,
                new IntentFilter(GcmPreferences.COMPLETE));
    }

    /**
     * Pause상태 시 LocalBroadCast 등록해지
     */
    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mGcm_BroadcastReceiver);
    }
}
