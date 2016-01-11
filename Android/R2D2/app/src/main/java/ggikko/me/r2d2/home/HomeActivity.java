package ggikko.me.r2d2.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ggikko.me.r2d2.R;
import ggikko.me.r2d2.api.restaurant.RestaurantAPI;
import ggikko.me.r2d2.api.user.UserAPI;
import ggikko.me.r2d2.around.AroundActivity;
import ggikko.me.r2d2.domain.BaseDto;
import ggikko.me.r2d2.domain.RestaurantDto;
import ggikko.me.r2d2.domain.UserDto;
import ggikko.me.r2d2.map.MapActivity;
import ggikko.me.r2d2.push.PushSettingActivity;
import ggikko.me.r2d2.subway.SubwayActivty;
import ggikko.me.r2d2.user.JoinActivity;
import ggikko.me.r2d2.user.LoginActivity;
import ggikko.me.r2d2.util.ResultCodeCollections;
import ggikko.me.r2d2.util.RetrofitInstance;
import ggikko.me.r2d2.util.SharedInformation;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 로그인 후 첫 홈 화면 Activity
 */
public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private Button btn_selected_subway;
    private String subwayName;

    RestaurantListFragment restaurantListFragment;

    private static String TAG = "HomeActivity";

    /**
     * 역 설정 버튼을 누른 후 역 값을 받아온다
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {

            /** 역 설정의 결과 받는 CODE - ResultCodeCollections.RESULTCODE_JOINACTIVITY_SUBWAY = 0 */
            case 0: {
                if(data != null) {
                    subwayName = data.getStringExtra("subway");
                    btn_selected_subway.setText(subwayName);
                    break;
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /** 전 페이지로부터 첫 번째로 보여줄 지하철 역을 받는다 */
        getSubwayNumberFromBeforePage();

        /** Toolbar 설정 */
        toolbarSetting();

        /** Toolbar를 Customize하기 ActionBar를 얻어오고, Home Indicator를 이용하여 네비게이션 뷰와 연동  */
        actionBarSetting();

        /** 네비게이션 뷰 설정 */
        navigationViewSetting();

        /** 뷰페이저 설정 */
        viewpagerSetting();

        /** 역 설정을 위한 버튼 설정 */
        buttonSettingForSubway();

    }

    private void getSubwayNumberFromBeforePage() {
        Intent intent = getIntent();
        subwayName = intent.getStringExtra("subwayNumber");
    }

    private void toolbarSetting() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.mToolbar);
        setSupportActionBar(toolbar);
    }

    private void actionBarSetting() {
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void navigationViewSetting() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.navigation_view);
        if (navView != null) {
            /** Drawer 내부의 내용 및 이벤트 설정 */
            setupDrawerContent(navView);
        }
    }

    private void viewpagerSetting() {
        viewPager = (ViewPager) findViewById(R.id.tab_viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
    }

    private void buttonSettingForSubway() {
        btn_selected_subway = (Button) findViewById(R.id.btn_selected_subway);
        btn_selected_subway.setOnClickListener(v -> goToSubwayActivity());
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        restaurantListFragment = RestaurantListFragment.getInstance();
        adapter.addFrag(restaurantListFragment, "맛집 리스트");
        viewPager.setAdapter(adapter);
    }

    /**
     * ViewPager Adapter, 기존에 맛집 리스트 1개 밖에 없지만 확장성을 고려하여 페이징은 ViewPager를 기본으로 한다.
     */
    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        /**
         * 뷰페이즈 페이지 수 설정
         */
        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        /**
         * 뷰페이저 list + title 추가
         */
        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        /**
         * 뷰페이저 타이틀 설정
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }


    /**
     * 네비게이션 드로어 부분입니다. 각 메뉴 부분을 설정할 수 있고, 이벤트를 걸 수 있습니다. FLAG는 각 메뉴 아이템들의 아이디 입니다.
     *
     * @param navigationView
     */
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);

                /** 메뉴(네비게이션 뷰에 리스트 부분) 버튼 클릭 시 */
                switch (menuItem.getItemId()) {

                    /** 홈 버튼 클릭 시 */
                    case R.id.home:

                        break;

                    case R.id.map:

                        /** 다음 화면 페이지 넘어간다(지도 화면) */
                        goToMapActivity();

                        break;

                    case R.id.around:

                        /** 다음 화면 페이지 넘어간다(내 주변 맛집 화면) */
                        goToAroundActivity();
                        break;

                    case R.id.push:

                        /** 다음 화면 페이지 넘어간다(푸쉬 셋팅 화면) */
                        goToPushSettingActivity();
                        break;

                    case R.id.help:

                        /** 이메일로 문의하도록 액션 인텐트를 취합니다 */
                        goToHelp();
                        break;
                }

                drawerLayout.closeDrawers();
                return true;
            }

            private void goToMapActivity() {
                Intent intent_map = new Intent(HomeActivity.this, MapActivity.class);
                startActivity(intent_map);
            }

            private void goToAroundActivity() {
                Intent intent_around = new Intent(HomeActivity.this, AroundActivity.class);
                startActivity(intent_around);
            }

            private void goToPushSettingActivity() {
                Intent intent_push = new Intent(HomeActivity.this, PushSettingActivity.class);
                startActivity(intent_push);
            }

            private void goToHelp() {
                String[] email = {"ggikko2@naver.com"};
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL,email);
                intent.putExtra(Intent.EXTRA_SUBJECT, "R2D2 관련 문의");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu main
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void requestRestaurants() {
        SharedInformation sharedInformation = SharedInformation.getInstance();
        String token = sharedInformation.getToken(HomeActivity.this);

        if (!token.equals("R2D2")) {

            /** 로그인에 사용할 retrofit instance 얻어옴 */
            RetrofitInstance retrofitInstance = RetrofitInstance.getInstance();
            Retrofit retrofit = retrofitInstance.getRestaurantList();

            /** 임시로 역 번호로 변경 */
            if(subwayName.contains("강남역"))subwayName = "gangnam";
            if(subwayName.contains("역삼역"))subwayName = "yeoksam";
            if(subwayName.contains("선릉역"))subwayName = "seolleung";

            RestaurantDto.GetRestaurants getRestaurants = new RestaurantDto.GetRestaurants(subwayName, token);
            RestaurantAPI restaurantAPI = retrofit.create(RestaurantAPI.class);

            Call<RestaurantDto.GetRestaurantsResponse> logonCall = restaurantAPI.reqRestaurants(getRestaurants);

            Log.e("ggikko", "homeactivity's subwayname2222 = " + getRestaurants.getSubwayNumber());
            Log.e("ggikko", "homeactivity's subwayname3 = " + getRestaurants.getUserId());

            /** retrofit 콜백 메소드 성공시 onResponse, 실패시 onFailure */
            logonCall.enqueue(new Callback<RestaurantDto.GetRestaurantsResponse>() {

                @Override
                public void onResponse(Response<RestaurantDto.GetRestaurantsResponse> response, Retrofit retrofit) {
                    RestaurantDto.GetRestaurantsResponse body = response.body();
                    Log.e("ggikko", "ok");
                    if (body != null) {
                        Log.e("ggikko", "body exist");

                        restaurantListFragment.changeListData(body);

                    } else {
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                }
            });
        }
    }

    /**
     * 다음 화면 페이지 넘어간다(역 설정 화면)
     */
    private void goToSubwayActivity() {
        Intent intent = new Intent(HomeActivity.this, SubwayActivty.class);
        String subway = btn_selected_subway.getText().toString();
        intent.putExtra("subway", subway);
        startActivityForResult(intent, ResultCodeCollections.RESULTCODE_HOMEACTIVITY_SUBWAY);
    }

    /**
     * resume 상태마다 맛집 정보를 불러온다.
     */
    @Override
    protected void onResume() {
        super.onResume();
        requestRestaurants();
    }
}
