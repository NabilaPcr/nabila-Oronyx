package com.example.bila_oronyx.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.bila_oronyx.receiver.ReminderReceiver
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ReminderHelper(private val context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    companion object {
        private const val TAG = "ReminderHelper"
    }

    fun setReminderAtTime(title: String, message: String, calendar: Calendar) {
        Log.d(TAG, "📅 Reminder diatur untuk: ${formatTime(calendar.timeInMillis)}")

        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("title", title)
            putExtra("message", message)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
            Log.d(TAG, "✅ Alarm set (M+) untuk: ${formatTime(calendar.timeInMillis)}")
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
            Log.d(TAG, "✅ Alarm set untuk: ${formatTime(calendar.timeInMillis)}")
        }
    }

    private fun formatTime(timeInMillis: Long): String {
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return format.format(timeInMillis)
    }
}