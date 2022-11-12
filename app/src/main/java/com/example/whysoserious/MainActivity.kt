package com.example.whysoserious

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    private val CHANNEL_ID = "testChannel01"   // Channel for notification
    private var notificationManager: NotificationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel(CHANNEL_ID, "testChannel", "this is a test Channel")

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            displayNotification()
        }






    }

    private fun displayNotification() {
        val notificationId = 45

        var notification = Notification.Builder(this, CHANNEL_ID) // 알림객체
            .setSmallIcon(android.R.drawable.star_on) // 작은아이콘
            .setContentTitle("한번 미소를 지어보세요! :)") //제목
            .setContentText("마스크를 쓰고있다면 쉽게 할수있겠네요..!") //본문텍스트
//            .setStyle(NotificationCompat.BigTextStyle().bigText("내가 좀 말이 많죠?!"))
            .setPriority(Notification.PRIORITY_DEFAULT)
            .build()

        notificationManager?.notify(notificationId, notification)
    }

    private fun createNotificationChannel(channelId: String, name: String, channelDescription: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT // set importance
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = channelDescription
            }
            // Register the channel with the system
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager?.createNotificationChannel(channel)
        }
    }
}