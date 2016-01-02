package ggikko.me.r2d2.user;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.mToolbar_join);
        setSupportActionBar(toolbar);

        // 툴바를 커스터마이징하기 위해 v7에서 제공하는 Action bar를 불러온다.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        edit_select_subway = (EditText) findViewById(R.id.edit_select_subway);

        edit_select_subway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectSubway = new Intent(JoinActivity.this, SubwayActivty.class);
                startActivity(selectSubway);
            }
        });
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
