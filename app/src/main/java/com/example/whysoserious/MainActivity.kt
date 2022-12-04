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
            val toastMessage = if (check) {

                // Set the alarm to start at approximately 2:00 p.m.
                val calendar1: Calendar = Calendar.getInstance().apply {
                    timeInMillis = System.currentTimeMillis()
                    set(Calendar.HOUR_OF_DAY, 15)
                    set(Calendar.MINUTE, 37)
                }

                val calendar2: Calendar = Calendar.getInstance().apply {
                    timeInMillis = System.currentTimeMillis()
                    set(Calendar.HOUR_OF_DAY, 15)
                    set(Calendar.MINUTE, 38)
                }

                val calendar3: Calendar = Calendar.getInstance().apply {
                    timeInMillis = System.currentTimeMillis()
                    set(Calendar.HOUR_OF_DAY, 15)
                    set(Calendar.MINUTE, 39)
                }

//                val triggerT1 = System.currentTimeMillis() + 5 * 1000
//                val triggerT2 = System.currentTimeMillis() + 10  * 1000
//                val triggerT3 = System.currentTimeMillis() + 15  * 1000

                // With setInexactRepeating(), you have to use one of the AlarmManager interval
                // constants--in this case, AlarmManager.INTERVAL_DAY.
                alarmManager1.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar1.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent1
                )

                alarmManager2.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar2.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent2
                )

                alarmManager3.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar3.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent3
                )


//                alarmManager1.set(
//                    AlarmManager.RTC_WAKEUP,
//                    //calendar1.timeInMillis,
//                    triggerT1,
//                    //AlarmManager.INTERVAL_FIFTEEN_MINUTES,
//                    pendingIntent1
//                )
//
//                alarmManager2.set(
//                    AlarmManager.RTC_WAKEUP,
//                    //calendar2.timeInMillis,
//                    triggerT2,
//                    //AlarmManager.INTERVAL_FIFTEEN_MINUTES,
//                    pendingIntent2
//                )
//
//                alarmManager3.set(
//                    AlarmManager.RTC_WAKEUP,
//                    //calendar3.timeInMillis,
//                    triggerT3,
//                    //AlarmManager.INTERVAL_FIFTEEN_MINUTES,
//                    pendingIntent3
//                )

                "알림 예약이 시작되었습니다."
            } else {
                alarmManager1.cancel(pendingIntent1)
                alarmManager2.cancel(pendingIntent2)
                alarmManager3.cancel(pendingIntent3)
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

