package ggikko.me.r2d2.around;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;

import ggikko.me.r2d2.R;

/**
 * 내 주변 맛집 변경 화면 Activity
 */
public class AroundActivity extends AppCompatActivity {

    Button btn_around;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_around);

        /** 툴바 셋팅 : 툴바를 커스터마이징하기 위해 v7에서 제공하는 Action bar를 불러온다. */
        toolbarSetting();

        btn_around = (Button) findViewById(R.id.btn_around);
        btn_around.setOnClickListener(v -> {

            finish();
        });

    }

    private void toolbarSetting() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.mToolbar_around);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
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
