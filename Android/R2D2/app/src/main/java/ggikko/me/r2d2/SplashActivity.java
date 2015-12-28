package ggikko.me.r2d2;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import ggikko.me.r2d2.home.HomeActivity;

/**
 * 처음 애플리케이션 진입점
 * 애니메이션을 3초동안 보여주고 Home Activity 로 전환시켜준다.
 */
public class SplashActivity extends AppCompatActivity {

    private ImageView iv_splash;

    private AnimationDrawable animation;

    private Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        iv_splash = (ImageView) findViewById(R.id.iv_splash);
        animation = (AnimationDrawable) iv_splash.getBackground();

        threadSetting();
        thread.start();

    }


    /**
     * 새로운 쓰레드를 만들어서 이미지를 약 3초간 보여주도록 설정
     */
    private void threadSetting() {
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

    }

    /**
     *  Splash Activity 애니메이션을 동작하도록 한다.
     */
    private void startAnimation() {
        iv_splash.post(new Runnable() {
            @Override
            public void run() {
                animation.start();
            }
        });
    }

    /**
     *  Splash Activity 애니메이션을 멈추도록 한다.
     */
    private void stopAnimation(){
        animation.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAnimation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(animation != null) stopAnimation();
    }
}
