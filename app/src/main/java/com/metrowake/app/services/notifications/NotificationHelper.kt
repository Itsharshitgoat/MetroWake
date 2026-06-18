package com.metrowake.app.services.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.metrowake.app.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        const val TRACKING_CHANNEL_ID = "tracking_channel"
        const val ALERT_CHANNEL_ID = "alert_channel"
        const val FOREGROUND_NOTIFICATION_ID = 1001
        const val ALERT_NOTIFICATION_ID = 1002
    }

    init {
        createChannels()
    }

    private fun createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val trackingChannel = NotificationChannel(
                TRACKING_CHANNEL_ID,
                "Journey Tracking",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Shows active journey progress"
                setShowBadge(false)
            }

            val alertChannel = NotificationChannel(
                ALERT_CHANNEL_ID,
                "Station Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Alerts when approaching destination"
                enableVibration(true)
            }

            manager.createNotificationChannel(trackingChannel)
            manager.createNotificationChannel(alertChannel)
        }
    }

    fun getForegroundNotification(content: String): Notification {
        return NotificationCompat.Builder(context, TRACKING_CHANNEL_ID)
            .setContentTitle("MetroWake Active")
            .setContentText(content)
            .setSmallIcon(R.mipmap.ic_launcher) // In real app, use a dedicated vector icon
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .build()
    }

    fun updateForegroundNotification(content: String) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(FOREGROUND_NOTIFICATION_ID, getForegroundNotification(content))
    }

    fun showStationAlert(title: String, message: String) {
        val notification = NotificationCompat.Builder(context, ALERT_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(ALERT_NOTIFICATION_ID, notification)
    }
}
