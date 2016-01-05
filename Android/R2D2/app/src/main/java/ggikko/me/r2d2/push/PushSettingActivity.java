package ggikko.me.r2d2.push;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import ggikko.me.r2d2.R;
import ggikko.me.r2d2.data.SubwayInformation;
import ggikko.me.r2d2.spinner.NiceSpinner;
import ggikko.me.r2d2.subway.SubwayActivty;
import ggikko.me.r2d2.util.ResultCodeCollections;

public class PushSettingActivity extends AppCompatActivity {

    NiceSpinner niceSpinner_hour;
    NiceSpinner niceSpinner_minutes;
    NiceSpinner niceSpinner_subway;

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
                niceSpinner_subway.setText(subway);
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_setting);

        /** 툴바 세팅 */
        toolbarSetting();

        /** 툴바를 커스터마이징하기 위해 v7에서 제공하는 Action bar를 불러온다. */
        actionbarSetting();

        /** Customizing 스피너 */
        spinnerSetting();
    }

    private void toolbarSetting() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.mToolbar_push);
        setSupportActionBar(toolbar);
    }

    private void actionbarSetting() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
    }

    private void spinnerSetting() {
        niceSpinner_hour = (NiceSpinner) findViewById(R.id.nice_spinner_hour);
        niceSpinner_minutes = (NiceSpinner) findViewById(R.id.nice_spinner_minutes);
        niceSpinner_subway = (NiceSpinner) findViewById(R.id.nice_spinner_subway);

        /** 선택 가능한 시간 세팅 */
        List<String> dataset_hour = new LinkedList<>
                (Arrays.asList("00", "01", "02", "03", "04", "05","06","07","08","09","10","11","12","13","14","15","16"
                        ,"17","18","19","20","21","22","23"));

        /** 선택 가능한 분 세팅 */
        List<String> dataset_minutes = new LinkedList<>
                (Arrays.asList("00", "10", "20", "30", "40", "50"));

        /** 선택 가능한 분 세팅 */
        List<String> dataset_subway = new LinkedList<>
                (Arrays.asList("역 설정"));

        /** 데이터 셋 적용 */
        niceSpinner_hour.attachDataSource(dataset_hour);
        niceSpinner_minutes.attachDataSource(dataset_minutes);
        niceSpinner_subway.attachDataSource(dataset_subway);

        niceSpinner_subway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PushSettingActivity.this, SubwayActivty.class);
                startActivityForResult(intent, ResultCodeCollections.RESULTCODE_HOMEACTIVITY_SUBWAY);
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


