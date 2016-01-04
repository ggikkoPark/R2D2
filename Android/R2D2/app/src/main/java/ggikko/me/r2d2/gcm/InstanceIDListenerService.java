package ggikko.me.r2d2.gcm;

import android.content.Intent;

/**
 * Created by ggikko on 16. 1. 4..
 */
public class InstanceIDListenerService extends com.google.android.gms.iid.InstanceIDListenerService {

    /** nstance ID를 얻을 리스너를 상속받고 토큰 갱신함 */
    private static final String TAG = "InstanceIDListenerService";

    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, RegisterationIntentService.class);
        startService(intent);
    }
}

