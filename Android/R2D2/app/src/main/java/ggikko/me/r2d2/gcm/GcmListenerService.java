package ggikko.me.r2d2.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import ggikko.me.r2d2.R;
import ggikko.me.r2d2.map.MapActivity;

/**
 * Created by ggikko on 16. 1. 4..
 */
public class GcmListenerService extends com.google.android.gms.gcm.GcmListenerService {

    /** 디바이스에 받은 메세지를 내용을 정의하는 클래스 */
    private static final String TAG = "GcmListenerService";
    private static final int REQUEST_CODE = 0;
    private static final int NOTIFICATION_ID = 0;

    @Override
    public void onMessageReceived(String from, Bundle data) {

        String title = data.getString("title");
        String message = data.getString("message");

        try {

            String getTitle = URLDecoder.decode(title, "UTF-8");
            String getMessage = URLDecoder.decode(message, "UTF-8");
            sendNotification(getTitle, getMessage);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    /** Notification 띄어주는 클래스 */
    private void sendNotification(String title, String message) {
        Intent intent = new Intent(this, MapActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, intent,
                PendingIntent.FLAG_ONE_SHOT);

        //Bitmap
        Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.r2d2_24logo);
        Bitmap picture2 = BitmapFactory.decodeResource(getResources(), R.drawable.r2d2icon);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.r2d2_24logo)
                .setContentTitle(title)
                .setContentText(message)
                .setSound(defaultSoundUri)
                // - EXPANDED DEFAULT
//                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent);

        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle(notificationBuilder);
        style.bigLargeIcon(picture)
                .bigPicture(picture2)
                .setBigContentTitle("R2D2")
                .setSummaryText("주인님!! 맛집 정보입니다!!");

        notificationBuilder.setStyle(style);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }
}

