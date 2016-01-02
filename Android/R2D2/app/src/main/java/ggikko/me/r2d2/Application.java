package ggikko.me.r2d2;

import ggikko.me.r2d2.util.FontOverride;

/**
 * Created by ggikko on 16. 1. 2..
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /** 나눔 폰트를 기본으로 설정한다 */
        FontOverride.setDefaultFont(this, "monospace", "nanumfont.otf");
    }
}
