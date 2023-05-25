package com.danil.ogranizertusur.workspace.alarm_scheduler

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.danil.ogranizertusur.R


class AlarmReceiver: BroadcastReceiver() {

    @SuppressLint("SuspiciousIndentation")
    override fun onReceive(context: Context?, intent: Intent?) {
      val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return
        val builder = NotificationCompat.Builder(context!!,"channelFirst")
            .setSmallIcon(R.drawable.tusur_embl)
            .setContentTitle("Ваша задача стартует прямо сейчас!!!")
            .setContentText("$message")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(message.length,builder.build())
    }
}