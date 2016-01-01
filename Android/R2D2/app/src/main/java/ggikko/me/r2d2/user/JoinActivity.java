package ggikko.me.r2d2.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import ggikko.me.r2d2.R;
import ggikko.me.r2d2.subway.SubwayActivty;

/**
 * 회원가입 화면 Activity
 */
public class JoinActivity extends AppCompatActivity {

    EditText edit_select_subway;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        edit_select_subway = (EditText) findViewById(R.id.edit_select_subway);

        edit_select_subway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectSubway = new Intent(JoinActivity.this, SubwayActivty.class);
                startActivity(selectSubway);
            }
        });

    }
}
