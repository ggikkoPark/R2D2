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

        JoinValidators validators = new JoinValidators();

        emailIsOk = validators.checkEmail(email);
        pwdIsOk = validators.checkPassword(password);

        if (!emailIsOk) txt_login_email.setVisibility(View.VISIBLE);
        if (!pwdIsOk) txt_login_password.setVisibility(View.VISIBLE);

        if (emailIsOk && pwdIsOk) {
            login_progressBar.setVisibility(View.VISIBLE);
            getInstanceIdToken();
        }

    }

    /** 토큰을 얻어온다 */
    public void getInstanceIdToken() {
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(LoginActivity.this, RegisterationIntentService.class);
            startService(intent);
        }
    }

    /**
     * 구글 서비스 환경 체크
     */
    private boolean checkPlayServices() {
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
                    gcmToken = intent.getStringExtra("token");
                    requestLoginToServer(email, password, gcmToken);
                }
            }
        };
    }

    private void requestLoginToServer(String email, String password, String gcmToken){

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

                if (body != null) {

                    if (response.isSuccess()) {

                        Snackbar snackbar = Snackbar.make(btn_login, R.string.snack_login_success, Snackbar.LENGTH_LONG);
                        snackbar.show();

                        /** status code가 200, 201 - 성공시 토큰 저장 및 페이지 넘김 */
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

                        /** status code가 400, 401, 403, etc */
                        /** 중복된 아이디 */
                        if (body.getCode().equals("duplicated.username.exception")) {

                            login_progressBar.setVisibility(View.GONE);
                            txt_login_email.setVisibility(View.VISIBLE);
                            txt_login_email.setText(body.getMessage());
                            Snackbar snackbar = Snackbar.make(btn_login, R.string.snack_login_fail, Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

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
