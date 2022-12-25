package com.example.whysoserious

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.whysoserious.Constant.Companion.CHANNEL_ID
import com.example.whysoserious.Constant.Companion.NOTIFICATION_ID
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class Constant {
    companion object {
        // 아이디 선언
        const val NOTIFICATION_ID = 0
        const val CHANNEL_ID = "notification_channel"

        // 알림 시간 설정
        const val ALARM_TIMER = 5
    }
}

class MyReceiver : BroadcastReceiver() {

    lateinit var notificationManager: NotificationManager


    override fun onReceive(context: Context, intent: Intent) {
        notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE) as NotificationManager

        val id = intent.getIntExtra("id",0)
        val text = intent.getStringExtra("text")
        val hour = intent.getIntExtra("hour", -1)
        val minute = intent.getIntExtra("minute", -1)
        val temp = intent.getSerializableExtra("calendar")

        notificationManager.sendNotification(id, context)

        val alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager

        Log.d("hour and day", hour.toString() + " " + minute.toString())
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }
        calendar.add(Calendar.DAY_OF_MONTH, 1)

        var nextalarm = "" + calendar.get(Calendar.YEAR)
        nextalarm += " " + calendar.get(Calendar.MONTH)
        nextalarm += " " + calendar.get(Calendar.DAY_OF_MONTH)
        nextalarm += " " + calendar.get(Calendar.HOUR_OF_DAY)
        nextalarm += " " + calendar.get(Calendar.MINUTE)
        Log.d("next alarm ", nextalarm)
//        val dateTime = LocalDateTime.now()
//        val nextDate = dateTime.plusDays(1).toEpochSecond(ZoneOffset.UTC)

        val pendingIntent = PendingIntent.getBroadcast(
            context, id, intent,
            //PendingIntent.FLAG_NO_CREATE
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )

    }

    // Notification 등록
    private fun deliverNotification(context: Context){
        val contentIntent = Intent(context, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID, // requestCode
            contentIntent, // 알림 클릭 시 이동할 인텐트
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            /*
            1. FLAG_UPDATE_CURRENT : 현재 PendingIntent를 유지하고, 대신 인텐트의 extra data는 새로 전달된 Intent로 교체
            2. FLAG_CANCEL_CURRENT : 현재 인텐트가 이미 등록되어있다면 삭제, 다시 등록
            3. FLAG_NO_CREATE : 이미 등록된 인텐트가 있다면, null
            4. FLAG_ONE_SHOT : 한번 사용되면, 그 다음에 다시 사용하지 않음
             */
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.star_on) // 아이콘
            .setContentTitle("타이틀 입니다.") // 제목
            .setContentText("내용 입니다.") // 내용
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    private fun deliverNotification2(context: Context){
        val contentIntent = Intent(context, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            2, // requestCode
            contentIntent, // 알림 클릭 시 이동할 인텐트
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            /*
            1. FLAG_UPDATE_CURRENT : 현재 PendingIntent를 유지하고, 대신 인텐트의 extra data는 새로 전달된 Intent로 교체
            2. FLAG_CANCEL_CURRENT : 현재 인텐트가 이미 등록되어있다면 삭제, 다시 등록
            3. FLAG_NO_CREATE : 이미 등록된 인텐트가 있다면, null
            4. FLAG_ONE_SHOT : 한번 사용되면, 그 다음에 다시 사용하지 않음
             */
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.star_on) // 아이콘
            .setContentTitle("타이틀 입니다2222.") // 제목
            .setContentText("내용 입니다2222.") // 내용
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        notificationManager.notify(2, builder.build())
    }




    // Notification 을 띄우기 위한 Channel 등록
    fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID, // 채널의 아이디
                "채널 이름입니다.", // 채널의 이름
                NotificationManager.IMPORTANCE_HIGH
                /*
                1. IMPORTANCE_HIGH = 알림음이 울리고 헤드업 알림으로 표시
                2. IMPORTANCE_DEFAULT = 알림음 울림
                3. IMPORTANCE_LOW = 알림음 없음
                4. IMPORTANCE_MIN = 알림음 없고 상태줄 표시 X
                 */
            )
            notificationChannel.enableLights(true) // 불빛
            notificationChannel.lightColor = Color.RED // 색상
            notificationChannel.enableVibration(true) // 진동 여부
            notificationChannel.description = "채널의 상세정보입니다." // 채널 정보
            notificationManager.createNotificationChannel(
                notificationChannel)
        }
    }
}