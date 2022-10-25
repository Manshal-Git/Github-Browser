package com.manshal_khatri.githubbrowser

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.manshal_khatri.githubbrowser.util.Constants

class GithubBrowser : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(Constants.NOTIFICATION_WIDGET_UPDATE,
                "Widget Update",NotificationManager.IMPORTANCE_HIGH)
            channel.description = "This will shown when new commits are made"

            val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            mNotificationManager.createNotificationChannel(channel)
        }
    }

}