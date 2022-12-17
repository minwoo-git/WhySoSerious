package com.example.whysoserious

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import java.util.*

fun NotificationManager.sendNotification(notification_id: Int, applicationContext: Context)
{
    val random = Random()
    val num = random.nextInt(1000)
    val builder = NotificationCompat.Builder(
        applicationContext,
        "Notification_Channel_id"
    )
        .setSmallIcon(android.R.drawable.star_big_on) // 아이콘
        .setContentTitle(num.toString()) // 제목
        .setContentText("내용 입니다.") // 내용
        //.setContentIntent(contentPendingIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    notify(notification_id, builder.build())
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}