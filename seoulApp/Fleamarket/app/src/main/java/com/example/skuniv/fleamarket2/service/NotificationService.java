package com.example.skuniv.fleamarket2.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.example.skuniv.fleamarket2.R;
import com.example.skuniv.fleamarket2.model.jsonModel.ApplyJson;
import com.example.skuniv.fleamarket2.view.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.Map;

public class NotificationService extends FirebaseMessagingService {

    public static  int NOTIFICATION_ID = 1;
    SharedPreferences loginSetting;
    SharedPreferences.Editor editor;
    Gson gson = new Gson();
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Call method to generate notification
        String message = remoteMessage.getNotification().getBody();
        String title = remoteMessage.getNotification().getTitle();
        Log.i("getBody",remoteMessage.getNotification().getBody());
        Log.i("getTitle",remoteMessage.getNotification().getTitle());

        ApplyJson applyJson = gson.fromJson(message, ApplyJson.class);

        System.out.println("user shop========" +applyJson.getShop());
        System.out.println("user role========" +applyJson.getRole());
        System.out.println("user text========" +applyJson.getText());

        loginSetting = getSharedPreferences("loginSetting", MODE_PRIVATE);
        editor = loginSetting.edit();
        editor.putString("shop",applyJson.getShop());
        editor.putInt("role", applyJson.getRole());

        generateNotification(title, applyJson.getText());
    }

    private void generateNotification(String title, String massage) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Log.i("title",title);
        Log.i("body",massage);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(massage)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                ;

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotifyBuilder.build());

    }
}