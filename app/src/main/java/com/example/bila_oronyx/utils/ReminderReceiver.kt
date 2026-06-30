package com.example.bila_oronyx.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.bila_oronyx.utils.NotificationHelper

class ReminderReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "ReminderReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "🔔 ReminderReceiver DIPANGGIL!")

        val title = intent.getStringExtra("title") ?: "Reminder! 📌"
        val message = intent.getStringExtra("message") ?: "Jangan lupa cek catatanmu!"

        Log.d(TAG, " Title: $title")
        Log.d(TAG, " Message: $message")

        val notificationHelper = NotificationHelper(context)
        notificationHelper.showNotification(title, message)

        Log.d(TAG, "✅ Notifikasi dikirim!")
    }
}