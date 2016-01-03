package ggikko.me.r2d2.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ggikko.me.r2d2.R;
import ggikko.me.r2d2.data.SubwayInformation;
import ggikko.me.r2d2.subway.SubwayActivty;
import ggikko.me.r2d2.util.ResultCodeCollections;

/**
 * 회원가입 화면 Activity
 */
public class JoinActivity extends AppCompatActivity {

    EditText edit_join_email, edit_join_password, edit_join_password_check, edit_select_subway;

    TextView txt_join_email, txt_join_password, txt_join_passwordcheck, txt_join_subway;

    Button btn_join;

    /** 역 설정 버튼을 누른 후 역 값을 받아온다 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){

            /** 역 설정의 결과 받는 CODE - ResultCodeCollections.RESULTCODE_JOINACTIVITY_SUBWAY = 0 */
            case 0 :
            {
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.mToolbar_join);
        setSupportActionBar(toolbar);

        /** 툴바를 커스터마이징하기 위해 v7에서 제공하는 Action bar를 불러온다. */
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        /** edit text view setting */
        edit_join_email = (EditText) findViewById(R.id.edit_join_email);
        edit_join_password = (EditText) findViewById(R.id.edit_join_password);
        edit_join_password_check = (EditText) findViewById(R.id.edit_join_password_check);

        /** text view setting */
        txt_join_email = (TextView) findViewById(R.id.txt_join_email);
        txt_join_password = (TextView) findViewById(R.id.txt_join_password);
        txt_join_passwordcheck = (TextView) findViewById(R.id.txt_join_passwordcheck);
        txt_join_subway = (TextView) findViewById(R.id.txt_join_subway);

        /** 지하철 역설정 */
        edit_select_subway = (EditText) findViewById(R.id.edit_join_subway);

        edit_select_subway.setOnClickListener(v -> {
            Intent selectSubway = new Intent(JoinActivity.this, SubwayActivty.class);
            startActivityForResult(selectSubway, ResultCodeCollections.RESULTCODE_JOINACTIVITY_SUBWAY);
        });

        btn_join = (Button) findViewById(R.id.btn_join);
        btn_join.setOnClickListener(v -> {
            requestJoin();
        });
    }

    private void requestJoin() {

        boolean emailIsOk = false;
        boolean pwdIsOk = false;
        boolean pwdCheckIsOk = false;
        boolean subwayCheckOK = false;

        if(!emailIsOk) txt_join_email.setVisibility(View.VISIBLE);
        if(!pwdIsOk) txt_join_password.setVisibility(View.VISIBLE);
        if(!pwdCheckIsOk) txt_join_passwordcheck.setVisibility(View.VISIBLE);
        if(!subwayCheckOK) txt_join_email.setVisibility(View.VISIBLE);

        if(emailIsOk && pwdIsOk & pwdCheckIsOk & subwayCheckOK) requestJoinToServer();

    }

    private void requestJoinToServer() {

        String email = edit_join_email.getText().toString();
        String password = edit_join_password.getText().toString();

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("잠시만 기다려주세요.");
        pDialog.show();




    }

    /**
     * 툴바 왼쪽 상단 백버튼 이벤트 동작하도록 설정
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
