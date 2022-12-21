package com.example.whysoserious

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import java.util.*

fun NotificationManager.sendNotification(notification_id: Int, applicationContext: Context)
{
    val stringArray : Array<String> = arrayOf(
        "a", "b", "c", "d", "e", "f", "g", "h","i", "j", "k", "l", "m", "n", "o"
    )
    val random = Random()
    val num = random.nextInt(15)
    val builder = NotificationCompat.Builder(
        applicationContext,
        "Notification_Channel_id"
    )
        .setSmallIcon(android.R.drawable.star_big_on) // 아이콘
        .setContentTitle(notification_id.toString() + "Make a Smile") // 제목
        .setContentText(stringArray[num]) // 내용
        //.setContentIntent(contentPendingIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    notify(notification_id, builder.build())
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}