package ggikko.me.r2d2.gcm;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import ggikko.me.r2d2.R;

/**
 * Created by ggikko on 16. 1. 4..
 */
public class RegisterationIntentService extends IntentService {

    private static final String TAG = "RegisterationIntentService";

    public RegisterationIntentService() {
        super(TAG);
    }

    public RegisterationIntentService(String name) {
        super(name);
    }


    @SuppressLint("LongLogTag")
    @Override
    protected void onHandleIntent(Intent intent) {

        /** GCM Instance ID의 토큰패치, LocalBoardcast로 ProgressBar가 동작하도록 액션을 넘겨줌 */
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(GcmPreferences.GENERATING));

        InstanceID instanceID = InstanceID.getInstance(this);
        String token = null;
        try {
            synchronized (TAG) {
                /** GCM 앱을 등록, google-services.json을 이용해 SenderID를 가져온다. */
                String default_senderId = getString(R.string.gcm_defaultSenderId);
                /** scope는 "GCM" */
                String scope = GoogleCloudMessaging.INSTANCE_ID_SCOPE;
                /** Instance ID에 해당 토큰 생성 및 패치 */
                token = instanceID.getToken(default_senderId, scope, null);

                Log.i(TAG, "GCM Registration Token: " + token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /** GCM Instance ID에 해당 토큰을 획득, LocalBoardcast에 COMPLETE 액션 & 토큰 넘기기 */
        Intent completeIntent = new Intent(GcmPreferences.COMPLETE);
        completeIntent.putExtra("token", token);
        Log.e("ggikko", "token : -"+ token);
        LocalBroadcastManager.getInstance(this).sendBroadcast(completeIntent);

    }

}

