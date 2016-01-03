package ggikko.me.r2d2.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ggikko.me.r2d2.R;
import ggikko.me.r2d2.api.user.UserAPI;
import ggikko.me.r2d2.domain.UserDto;
import ggikko.me.r2d2.subway.SubwayActivty;
import ggikko.me.r2d2.util.JoinValidators;
import ggikko.me.r2d2.util.ResultCodeCollections;
import ggikko.me.r2d2.util.UserDeserializer;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 회원가입 화면 Activity
 */
public class JoinActivity extends AppCompatActivity {

    EditText edit_join_email, edit_join_password, edit_join_password_check, edit_select_subway;
    TextView txt_join_email, txt_join_password, txt_join_passwordcheck, txt_join_subway;
    Button btn_join;

    /**
     * 역 설정 버튼을 누른 후 역 값을 받아온다
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {

            /** 역 설정의 결과 받는 CODE - ResultCodeCollections.RESULTCODE_JOINACTIVITY_SUBWAY = 0 */
            case 0: {
                String subway = data.getStringExtra("subway");
                edit_select_subway.setText(subway);
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        /** Toolbar setting */
        toolbarSetting();

        /** edit text view setting */
        findEditText();

        /** text view setting */
        findTextView();

        /** 지하철 역설정 */
        selectSubwaySetting();

        /** 가입버튼 설정 */
        joinButtonSetting();
    }

    private void toolbarSetting() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.mToolbar_join);
        setSupportActionBar(toolbar);

        /** 툴바를 커스터마이징하기 위해 v7에서 제공하는 Action bar를 불러온다. */
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void findEditText() {
        edit_join_email = (EditText) findViewById(R.id.edit_join_email);
        edit_join_password = (EditText) findViewById(R.id.edit_join_password);
        edit_join_password_check = (EditText) findViewById(R.id.edit_join_password_check);
    }

    private void findTextView() {
        txt_join_email = (TextView) findViewById(R.id.txt_join_email);
        txt_join_password = (TextView) findViewById(R.id.txt_join_password);
        txt_join_passwordcheck = (TextView) findViewById(R.id.txt_join_passwordcheck);
        txt_join_subway = (TextView) findViewById(R.id.txt_join_subway);
    }

    private void selectSubwaySetting() {
        edit_select_subway = (EditText) findViewById(R.id.edit_join_subway);
        edit_select_subway.setOnClickListener(v -> {
            Intent selectSubway = new Intent(JoinActivity.this, SubwayActivty.class);
            startActivityForResult(selectSubway, ResultCodeCollections.RESULTCODE_JOINACTIVITY_SUBWAY);
        });
    }

    private void joinButtonSetting() {
        btn_join = (Button) findViewById(R.id.btn_join);
        btn_join.setOnClickListener(v -> {
            requestJoin(v);
        });
    }

    /**
     * 각 데이터 유효성 검사함
     * 검사가 통과되면 서버에 로그인 요청
     *
     * @param v
     */
    private void requestJoin(View v) {

        /** 유효성 검사를 위한 default setting */
        boolean emailIsOk = false;
        boolean pwdIsOk = false;
        boolean pwdCheckIsOk = false;
        boolean subwayCheckOK = false;

        /** 유효성 검사할 각 값들을 받아온다. */
        String email = edit_join_email.getText().toString();
        String password = edit_join_password.getText().toString();
        String passwordCheck = edit_join_password_check.getText().toString();
        String subway = edit_select_subway.getText().toString();

        /** 유효성 검사 */
        JoinValidators validators = new JoinValidators();
        emailIsOk = validators.checkEmail(email);
        pwdIsOk = validators.checkPassword(password);
        pwdCheckIsOk = validators.checkPasswordCheck(password, passwordCheck);
        subwayCheckOK = validators.checkSubway(subway);

        /** 유효성 검사 통과하지 못할경우 에러메시지를 EditText 아래에 Text형식으로 보여준다. */
        if (!emailIsOk) txt_join_email.setVisibility(View.VISIBLE);
        if (!pwdIsOk) txt_join_password.setVisibility(View.VISIBLE);
        if (!pwdCheckIsOk) txt_join_passwordcheck.setVisibility(View.VISIBLE);
        if (!subwayCheckOK) txt_join_email.setVisibility(View.VISIBLE);

        if (emailIsOk && pwdIsOk & pwdCheckIsOk & subwayCheckOK)
            requestJoinToServer(email, password, subway, v);
    }

    /**
     * 서버에 로그인 요청
     * request - UserDto.Create
     * response - UserDto.JoinResponse
     */
    private void requestJoinToServer(String email, String password, String subway, View v) {


        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("잠시만 기다려주세요.");
        pDialog.show();

        Gson gson = new GsonBuilder().registerTypeAdapter(UserDto.Create.class, new UserDeserializer()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlInformation.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        UserDto.Create createUser = new UserDto.Create(email, password, subway);
        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call<UserDto.JoinResponse> createUserCall = userAPI.createUser(createUser);

        /** retrofit 콜백 메소드 성공시 onResponse, 실패시 onFailure */
        createUserCall.enqueue(new Callback<UserDto.JoinResponse>() {
            @Override
            public void onResponse(Response<UserDto.JoinResponse> response, Retrofit retrofit) {

                pDialog.hide();

                int code = response.code();
                String message = response.message();

                UserDto.JoinResponse body = response.body();


                if (statusIsCreated(code)) {

                    Snackbar snackbar = Snackbar.make(v, R.string.snack_join_success, Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    /** status code가 200, 201 */
                    String userId = body.getUserId();

                    finish();

                } else {
                    /** status code가 400, 401, 403, etc */

                    /** 잘못된 요청 */
                    //TODO : bad request 처리
                    /** 중복된 아이디 */
//                    if (body.getCode().equals("duplicated.username.exception")) {
                    // TODO : snack bar로 바꿀 예정임

                    txt_join_email.setVisibility(View.VISIBLE);
                    txt_join_email.setText("이메일이 중복됩니다");

                    Snackbar snackbar = Snackbar.make(v, R.string.snack_join_fail, Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }


            /** 상태 코드가 CREATED 이면 */
            private boolean statusIsCreated(int code) {
                return code == 201;
            }


            @Override
            public void onFailure(Throwable t) {

                Snackbar snackbar = Snackbar.make(v, R.string.snack_join_servererror, Snackbar.LENGTH_SHORT);
                snackbar.show();

                pDialog.hide();
            }
        });

    }

    /**
     * 툴바 왼쪽 상단 백버튼 이벤트 동작하도록 설정
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
