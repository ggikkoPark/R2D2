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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ggikko.me.r2d2.R;
import ggikko.me.r2d2.around.AroundActivity;
import ggikko.me.r2d2.map.MapActivity;
import ggikko.me.r2d2.subway.SubwayActivty;
import ggikko.me.r2d2.util.ResultCodeCollections;

/**
 * 로그인 후 첫 홈 화면 Activity
 */
public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ViewPager viewPager;

    private static String TAG = "HomeActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

    private void toolbarSetting() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.mToolbar);
        setSupportActionBar(toolbar);
    }

    private void actionBarSetting() {
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void navigationViewSetting() {
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.navigation_view);
        if (navView != null){
            /** Drawer 내부의 내용 및 이벤트 설정 */
            setupDrawerContent(navView);
        }
    }

    private void viewpagerSetting() {
        viewPager = (ViewPager)findViewById(R.id.tab_viewpager);
        if (viewPager != null){
            setupViewPager(viewPager);
        }
    }

    private void buttonSettingForSubway() {
        Button btn_selected_subway = (Button) findViewById(R.id.btn_selected_subway);
        btn_selected_subway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SubwayActivty.class);
                startActivityForResult(intent, ResultCodeCollections.RESULTCODE_HOMEACTIVITY_SUBWAY);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new RestaurantListFragment(), "맛집 리스트");
        viewPager.setAdapter(adapter);
    }

    /**
     * ViewPager Adapter, 기존에 맛집 리스트 1개 밖에 없지만 확장성을 고려하여 페이징은 ViewPager를 기본으로 한다.
     */
    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager){
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        /** 뷰페이즈 페이지 수 설정 */
        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        /** 뷰페이저 list + title 추가 */
        public void addFrag(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        /** 뷰페이저 타이틀 설정 */
        @Override
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }

    }


    /**
     * 네비게이션 드로어 부분입니다. 각 메뉴 부분을 설정할 수 있고, 이벤트를 걸 수 있습니다. FLAG는 각 메뉴 아이템들의 아이디 입니다.
     * @param navigationView
     */
    private void setupDrawerContent(NavigationView navigationView){
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

                        Intent intent_map = new Intent(HomeActivity.this, MapActivity.class);
                        startActivity(intent_map);

                        break;

                    case R.id.around:

                        Intent intent_around = new Intent(HomeActivity.this, AroundActivity.class);
                        startActivity(intent_around);

                        break;

                    case R.id.push:

                        break;

                    case R.id.help:

                        break;
                }

                drawerLayout.closeDrawers();
                return true;
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

        switch (id){
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
