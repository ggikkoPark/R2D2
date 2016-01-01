package ggikko.me.r2d2.subway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import ggikko.me.r2d2.R;

/**
 * 원하는 지하철 역을 설정하고 이에 관련 데이터를 받는 화면
 */
public class SubwayActivty extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subway_actiity);

        /** 기본 툴바 설정 및 Actionbar 셋팅 */
        Toolbar toolbar = (Toolbar) findViewById(R.id.subway_toolbar);
        setSupportActionBar(toolbar);

        /** recyclerView 설정 */
        recyclerView = (RecyclerView) findViewById(R.id.subway_recyclerview);
        SubwayAdapter adapter = new SubwayAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }
}
