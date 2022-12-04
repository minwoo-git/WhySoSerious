package com.example.whysoserious

import android.app.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Toast
import android.widget.ToggleButton
import com.example.whysoserious.Constant.Companion.ALARM_TIMER
import com.example.whysoserious.Constant.Companion.NOTIFICATION_ID
import java.util.*
import kotlin.math.min

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val alarmManager1 = getSystemService(ALARM_SERVICE) as AlarmManager
        val alarmManager2 = getSystemService(ALARM_SERVICE) as AlarmManager
        val alarmManager3 = getSystemService(ALARM_SERVICE) as AlarmManager

        val intent1 = Intent(this,MyReceiver::class.java)
        intent1.putExtra("id",1111)
        intent1.putExtra("text","한번 가볍게 입꼬리만 살짝 올려보세요!1")

        val intent2 = Intent(this,MyReceiver::class.java)
        intent2.putExtra("id",2222)
        intent2.putExtra("text","한번 가볍게 입꼬리만 살짝 올려보세요!2")

        val intent3 = Intent(this,MyReceiver::class.java)
        intent3.putExtra("id",3333)
        intent3.putExtra("text","한번 가볍게 입꼬리만 살짝 올려보세요!3")

        val pendingIntent1 = PendingIntent.getBroadcast(
            this, 1, intent1,
            //PendingIntent.FLAG_NO_CREATE
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val pendingIntent2 = PendingIntent.getBroadcast(
            this, 2, intent2,
            //PendingIntent.FLAG_NO_CREATE
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val pendingIntent3 = PendingIntent.getBroadcast(
            this, 3, intent3,
            //PendingIntent.FLAG_NO_CREATE
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val button = findViewById<ToggleButton>(R.id.button)

        // 토글버튼 활성화 시 알림을 생성하고 토스트 메세지로 출력
        button.setOnCheckedChangeListener { _, check ->
            var toastMessage = "알람 중지"
            if (check)
            {
                toastMessage = "알람 예약"
            }
            makeNotification(1, "한번 가볍게 입꼬리만 올려보세요", 16, 6, check)
            makeNotification(2, "한번 가볍게 입꼬리만 올려보세요", 16, 7, check)
            makeNotification(3, "한번 가볍게 입꼬리만 올려보세요", 16, 8, check)


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
        val pendingIntent = PendingIntent.getBroadcast(
            this, id, intent,
            //PendingIntent.FLAG_NO_CREATE
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        if (check)
        {
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
        else
        {
            alarmManager.cancel(pendingIntent)
        }


    }

}

