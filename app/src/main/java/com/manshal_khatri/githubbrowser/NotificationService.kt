package com.manshal_khatri.githubbrowser

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

import androidx.core.app.NotificationCompat
import com.manshal_khatri.githubbrowser.util.Constants

class NotificationService(private val context: Context) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(owner:String, msg: String){

//        val channel = NotificationChannel(Constants.NOTIFICATION_WIDGET_UPDATE,
//            "Widget Update",NotificationManager.IMPORTANCE_HIGH)
//        channel.description = "This will shown when new commits are made"

        val notification = NotificationCompat.Builder(context,Constants.NOTIFICATION_WIDGET_UPDATE)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("New Commit by $owner")
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentText(msg)

//set intents and pending intents to call service on click of "dismiss" action button of notification
//        val dismissIntent =  Intent(context, NotificationService::class.java);
//        val piDismiss = PendingIntent.getService(context, 0, dismissIntent, 0);
//
//set intents and pending intents to call service on click of "snooze" action button of notification
//        val snoozeIntent = Intent(context,NotificationService::class.java);
//        snoozeIntent.setAction(ACTION_SNOOZE);
//        val piSnooze = PendingIntent.getService(context, 0, snoozeIntent, 0);
//
//        notification.addAction(R.drawable.ic_delete, "dismiss it", piDismiss)
//        notification.addAction(R.drawable.ic_back, "snooze it", piSnooze);

        notification.color = context.resources.getColor(R.color.teal_200)
//        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(1,notification.build())
    }
}