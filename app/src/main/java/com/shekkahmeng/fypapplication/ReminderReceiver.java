package com.shekkahmeng.fypapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

/**
 * Created by vc on 8/10/15.
 */
public class ReminderReceiver extends BroadcastReceiver {

    public final static String ALARM_EXTRA = "alarm_extra";

    @Override
    public void onReceive(Context context, Intent intent) {
        sendNotification(context, intent.getStringExtra(ALARM_EXTRA));
    }

    public static void sendNotification(Context context, String message) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(message)
//			.setContentText("setContentText")
//			.setContentInfo("setContentInfo")
                .setDefaults(Notification.DEFAULT_ALL)
                // Notification.DEFAULT_LIGHTS,
                // Notification.DEFAULT_SOUND,
                // Notification.DEFAULT_VIBRATE
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher);

        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.setAction(Intent.ACTION_MAIN);
        resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);

//        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
                0, resultIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }
}
