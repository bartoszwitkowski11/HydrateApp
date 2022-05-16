package com.example.hydrate

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.hydrate.activity.MainActivity

class ReminderBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {


       val resultIntent: Intent = Intent(context, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 1, resultIntent,PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationBuilder : NotificationCompat.Builder = NotificationCompat.Builder(context, "reminderAboutDrinking")
            .setSmallIcon(R.drawable.ic_baseline_local_drink)
            .setContentTitle("Hey, remember about drinking something!")
            .setContentText("It is important to stay hydrated")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

       val notificationManager : NotificationManagerCompat = NotificationManagerCompat.from(context)

        notificationManager.notify(11, notificationBuilder.build())
    }
}