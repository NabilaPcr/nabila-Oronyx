package com.example.bila_oronyx

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bila_oronyx.databinding.ActivityListBelajarBinding
import com.example.bila_oronyx.databinding.ActivityLoginOronyxBinding
import com.example.bila_oronyx.databinding.ActivityMainBinding
import com.example.bila_oronyx.databinding.ActivityProfilBinding
import com.example.nabila_sprinkle.RumusBangun // Pastikan package ini sesuai
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnRumus.setOnClickListener {
            val intent = Intent(this, RumusBangun::class.java)
            // Mengirim data judul dan deskripsi sesuai instruksi
            intent.putExtra("judul", "Kalkulator Geometrik")
            intent.putExtra("deskripsi", "Hitung luas dan volume dengan mudah")
            startActivity(intent)
        }

        binding.btnCustom1.setOnClickListener {
            val intent = Intent(this, Profil::class.java)
            intent.putExtra("judul", "Profil Pengguna")
            startActivity(intent)
        }

        binding.btnCustom2.setOnClickListener {
            val intent = Intent(this, ListBelajar::class.java)
            intent.putExtra("judul", "Daftar Tugas Belajar")
            startActivity(intent)
        }

        // Tombol 4: Logout dengan Alert Dialog & SnackBar
        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { _, _ ->
                    val intent = Intent(this, LoginOronyx::class.java) // Ganti sesuai nama activity loginmu
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                    Snackbar.make(binding.root, "Logout dibatalkan", Snackbar.LENGTH_SHORT).show()
                }
                .show()
        }
    }
}