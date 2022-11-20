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

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        val intent = Intent(this,MyReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this, NOTIFICATION_ID, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val button = findViewById<ToggleButton>(R.id.button)

        // 토글버튼 활성화 시 알림을 생성하고 토스트 메세지로 출력
        button.setOnCheckedChangeListener { _, check ->
            val toastMessage = if (check) {

                // Set the alarm to start at approximately 2:00 p.m.
                val calendar: Calendar = Calendar.getInstance().apply {
                    timeInMillis = System.currentTimeMillis()
                    set(Calendar.HOUR_OF_DAY, 14)
                    set(Calendar.MINUTE, 8)
                }

                // With setInexactRepeating(), you have to use one of the AlarmManager interval
                // constants--in this case, AlarmManager.INTERVAL_DAY.
                alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                    pendingIntent
                )

                "$ALARM_TIMER 초 후에 알림이 발생합니다."
            } else {
                alarmManager.cancel(pendingIntent)
                "알림 예약을 취소하였습니다."
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
}

