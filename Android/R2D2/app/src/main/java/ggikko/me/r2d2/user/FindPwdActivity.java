package ggikko.me.r2d2.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import ggikko.me.r2d2.R;

public class FindPwdActivity extends AppCompatActivity {

    Button btn_findpwd_request;
    EditText edit_findpwd_name, edit_findpwd_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);

        btn_findpwd_request = (Button) findViewById(R.id.btn_findpwd_request);
        edit_findpwd_name = (EditText) findViewById(R.id.edit_findpwd_name);
        edit_findpwd_email = (EditText) findViewById(R.id.edit_findpwd_email);

        btn_findpwd_request.setOnClickListener(v -> findPwdRequest());
    }

    private void findPwdRequest() {
        finish();
    }
}
