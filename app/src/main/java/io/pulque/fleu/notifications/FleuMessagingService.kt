package io.pulque.fleu.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import io.pulque.fleu.R
import io.pulque.fleu.ui.HomeActivity
import timber.log.Timber


/*
 * @author savirdev on 2019-11-24
 */

private const val FLEU_REMINDER_CHANNEL_NAME = "Fleu Reminder"
private const val FLEU_REMINDER_CHANNEL_DESC = "Fleu Reminder Channel"
private const val FLEU_REMINDER_CHANNEL_ID = "FLEU_REMINDER_CHANNEL_ID"
private const val FLEU_REMINDER_NOTIFICATION_ID = 1000

class FleuMessagingService : FirebaseMessagingService(){

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        createNotificationChannel()

        val title = remoteMessage.data["title"]
        val body = remoteMessage.data["body"]

        val intent = Intent(this, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, FLEU_REMINDER_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(FLEU_REMINDER_NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(FLEU_REMINDER_CHANNEL_ID, FLEU_REMINDER_CHANNEL_NAME, importance).apply {
                description = FLEU_REMINDER_CHANNEL_DESC
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onNewToken(p0: String) {
        Timber.d(p0)
    }
}