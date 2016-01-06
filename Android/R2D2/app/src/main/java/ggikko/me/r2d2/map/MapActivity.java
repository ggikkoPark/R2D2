package ggikko.me.r2d2.map;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ggikko.me.r2d2.R;

/**
 * 맛집 정보를 뿌려줄 지도 Activity
 */
public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        /** 툴바 세팅 */
        toolbarSetting();

        /** 툴바를 커스터마이징하기 위해 v7에서 제공하는 Action bar를 불러온다. */
        actionbarSetting();


    }

    private void toolbarSetting() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.mToolbar_map);
        setSupportActionBar(toolbar);
    }

    private void actionbarSetting() {
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
