package com.test.newsapp.fcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.test.newsapp.MainActivity
import com.test.newsapp.R
import com.test.newsapp.utils.Utils

const val CLOUD_MESSAGING = "cloud_messaging"
const val CLOUD_MESSAGING_NAME = "News Notification channel"

/**
 * Google Cloud messaging service
 *
 * When notification is received, it creates a notification and shows it to the user
 */
class NewsMessagingService : FirebaseMessagingService() {

    /**
     * Method invoked when a new msg is received
     *
     * @param remoteMessage Message object
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        //  Create Notification and show
        sendNotification(remoteMessage)
    }

    /**
     * Creates notification
     *
     * @param remoteMessage Message data object
     */
    private fun sendNotification(remoteMessage: RemoteMessage) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utils.makeNotificationChannel(
                CLOUD_MESSAGING,
                CLOUD_MESSAGING_NAME,
                NotificationManager.IMPORTANCE_LOW,
                this
            )
        }

        val notification = remoteMessage.notification
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val notificationBuilder = NotificationCompat.Builder(this, CLOUD_MESSAGING)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(notification?.title)
            .setContentText(notification?.body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
        NotificationManagerCompat.from(this).notify(0, notificationBuilder.build())
    }
}