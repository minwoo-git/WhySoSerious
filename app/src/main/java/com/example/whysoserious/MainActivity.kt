package com.example.whysoserious

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import java.util.*

// alarmFix branch created
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 아래 채널 id 는 다른 곳에서도 똑같아야함.
        createChannel("Notification_Channel_id", "Notification_Channel")

        val imageview = findViewById<ImageView>(R.id.imageView)
        if(UpdateState("Test")) {
            imageview.setImageResource(R.drawable.smileface);
        }
        else {
            imageview.setImageResource(R.drawable.sadface);
        }

        val button = findViewById<ToggleButton>(R.id.button)
        button.isChecked = UpdateState("Test")
        // 토글버튼 활성화 시 알림을 생성하고 토스트 메세지로 출력
        button.setOnCheckedChangeListener { _, check ->
            RadioStateSave("Test", check)

            var toastMessage = "알람 중지"
            if (check)
            {
                toastMessage = "알람 예약"
            }

            makeNotification(1, "한번 가볍게 입꼬리만 올려보세요", 8, 45, check)
            makeNotification(2, "한번 가볍게 입꼬리만 올려보세요", 13, 10, check)
            makeNotification(3, "한번 가볍게 입꼬리만 올려보세요", 18, 10, check)

            if(check) {
                imageview.setImageResource(R.drawable.smileface);
            }
            else {
                imageview.setImageResource(R.drawable.sadface);
            }

            /*
            1. ELAPSED_REALTIME : ELAPSED_REALTIME 사용. 절전모드에 있을 때는 알람을 발생시키지 않고 해제되면 발생시킴.
            2. ELAPSED_REALTIME_WAKEUP : ELAPSED_REALTIME 사용. 절전모드일 때도 알람을 발생시킴.
            3. RTC : Real Time Clock 사용. 절전모드일 때는 알람을 발생시키지 않음.
            4. RTC_WAKEUP : Real Time Clock 사용. 절전모드 일 때도 알람을 발생시킴.
             */

            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        }
    }

    fun makeNotification(id : Int, text : String, hour : Int, minute : Int, check : Boolean)
    {
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, MyReceiver::class.java)

        intent.putExtra("id", id)
        intent.putExtra("text", text + id)
        intent.putExtra("hour", hour)
        intent.putExtra("minute", minute)
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            this, id, intent,
            //PendingIntent.FLAG_NO_CREATE
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        if (check)
        {
//            alarmManager.setInexactRepeating(
//                AlarmManager.RTC_WAKEUP,
//                calendar.timeInMillis,
//                AlarmManager.INTERVAL_DAY,
//                pendingIntent
//            )
//            alarmManager.setExact(
//                AlarmManager.RTC_WAKEUP,
//                calendar.timeInMillis,
//                pendingIntent
//            )
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )

        }
        else
        {
            alarmManager.cancel(pendingIntent)
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.cancelNotifications()
        }


    }

    private fun RadioStateSave(key: String, value: Boolean) {
        val sharedPreferences = getSharedPreferences(
            "Button_State",
            MODE_PRIVATE
        ) // "Button_State"라는 이름으로 파일생성, MODE_PRIVATE는 자기 앱에서만 사용하도록 설정하는 기본값
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value) // 키와 값을 boolean으로 저장
        editor.apply() // 실제로 저장
    }
    private fun UpdateState(key: String): Boolean {
        val sharedPreferences = getSharedPreferences("Button_State", MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, false)
    }

    private fun createChannel(channelId: String, channelName: String) {
        // TODO: Step 1.6 START create a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                // TODO: Step 2.4 change importance
                NotificationManager.IMPORTANCE_HIGH
            )// TODO: Step 2.6 disable badges for this channel
                .apply {
                    setShowBadge(false)
                }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Notification Channel 입니다. "

            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)

        }
        // TODO: Step 1.6 END create a channel
    }

}

