package com.example.pixquest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String title = intent.getStringExtra("TITLE");

        NotifHelper notifHelper = new NotifHelper(context);
        NotificationCompat.Builder nb= notifHelper.getChannelNotification("REMINDER", title);
        notifHelper.getManager().notify(1, nb.build());

    }
}
