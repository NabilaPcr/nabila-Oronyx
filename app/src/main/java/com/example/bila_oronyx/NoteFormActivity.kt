package com.example.bila_oronyx

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.bila_oronyx.data.AppDatabase
import com.example.bila_oronyx.data.entity.NoteEntity
import com.example.bila_oronyx.databinding.ActivityNoteFormBinding  // 🔥 Pakai binding yang benar
import com.example.bila_oronyx.utils.NotificationHelper
import com.example.bila_oronyx.utils.ReminderHelper
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NoteFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteFormBinding  // 🔥 ActivityNoteFormBinding
    private lateinit var db: AppDatabase
    private lateinit var notificationHelper: NotificationHelper
    private lateinit var reminderHelper: ReminderHelper

    private var selectedReminderTime: Calendar? = null
    private var isReminderEnabled: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteFormBinding.inflate(layoutInflater)  // 🔥 inflate layout activity_note_form.xml
        setContentView(binding.root)

        notificationHelper = NotificationHelper(this)
        reminderHelper = ReminderHelper(this)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        db = AppDatabase.getInstance(this)

        // Switch Reminder
        binding.switchReminder.setOnCheckedChangeListener { _, isChecked ->
            isReminderEnabled = isChecked
            if (!isChecked) {
                binding.tvReminderTime.text = "🕐 Reminder dimatikan"
                binding.tvReminderTime.setTextColor(getColor(android.R.color.darker_gray))
                binding.btnSetReminder.isEnabled = false
            } else {
                binding.tvReminderTime.text = "🕐 Belum diatur"
                binding.tvReminderTime.setTextColor(getColor(R.color.sida_text_medium))
                binding.btnSetReminder.isEnabled = true
            }
        }

        // Tombol Set Reminder
        binding.btnSetReminder.setOnClickListener {
            showTimePickerDialog()
        }

        // Tombol Save
        binding.btnSaveNote.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val content = binding.etContent.text.toString()

            if (title.isNotBlank() && content.isNotBlank()) {
                lifecycleScope.launch {
                    val note = NoteEntity(
                        title = title,
                        content = content,
                        createdAt = System.currentTimeMillis()
                    )
                    db.noteDao().insert(note)

                    notificationHelper.showNotification(
                        title = "Catatan Tersimpan! 📝",
                        message = "Catatan '$title' berhasil disimpan."
                    )

                    if (isReminderEnabled && selectedReminderTime != null) {
                        val calendar = selectedReminderTime!!
                        if (calendar.timeInMillis > System.currentTimeMillis()) {
                            reminderHelper.setReminderAtTime(
                                title = "Jangan Lupa! 📌",
                                message = "Baca catatan '$title' yang baru kamu buat.",
                                calendar = calendar
                            )
                            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                            Toast.makeText(
                                this@NoteFormActivity,
                                "⏰ Reminder diatur jam ${timeFormat.format(calendar.time)}",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                this@NoteFormActivity,
                                "⚠️ Waktu harus di masa depan!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else if (isReminderEnabled && selectedReminderTime == null) {
                        Toast.makeText(
                            this@NoteFormActivity,
                            "⚠️ Atur waktu reminder dulu!",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@launch
                    }

                    finish()
                }
            } else {
                Toast.makeText(this, "Isi semua kolom!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
                val selectedCalendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, selectedHour)
                    set(Calendar.MINUTE, selectedMinute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }

                if (selectedCalendar.timeInMillis <= System.currentTimeMillis()) {
                    selectedCalendar.add(Calendar.DAY_OF_MONTH, 1)
                }

                selectedReminderTime = selectedCalendar

                val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                binding.tvReminderTime.text = "🕐 ${timeFormat.format(selectedCalendar.time)}"
                binding.tvReminderTime.setTextColor(getColor(R.color.sida_dark_green))

                Toast.makeText(
                    this,
                    "⏰ Reminder diatur jam ${timeFormat.format(selectedCalendar.time)}",
                    Toast.LENGTH_SHORT
                ).show()
            },
            hour,
            minute,
            true
        )

        timePickerDialog.setTitle("Pilih Waktu Reminder")
        timePickerDialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}