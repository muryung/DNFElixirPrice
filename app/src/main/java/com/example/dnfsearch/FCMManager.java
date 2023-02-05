package com.example.dnfsearch;

import static android.content.ContentValues.TAG;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FCMManager {
    OkHttpClient okHttpClient;

    String channelId = "110";
    String channelName = "MyChannel";
    String description = "Description";

    public void InitialChannel(Context context)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_NONE;

            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
    // From https://vagabond95.me/posts/notification-with-oreo/

    public void GetDeviceToken()
    {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "Failed Generating Token", task.getException());
                    return;
                }

                String token = task.getResult();
                System.out.println(token);
            }
        });
    }

    public void SendNotification(String deviceToken, String firebaseKey, String title, String body) {
        okHttpClient = new OkHttpClient();

        String requestJsonString =
                "{\"to\":\""+ deviceToken +"\",\"notification\":{\"body\":\""+body+"\",\"title\":\""+title+"\"}}";
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestJsonString);
        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .post(requestBody)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Authorization", "key=" + firebaseKey)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.body().toString());
            }
        });
    }

}
