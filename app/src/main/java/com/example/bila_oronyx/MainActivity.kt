package com.example.bila_oronyx

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bila_oronyx.databinding.ActivityMainBinding
import com.example.bila_oronyx.pertemuan2.RumusBangun
import com.example.bila_oronyx.pertemuan3.LoginOronyx
import com.example.bila_oronyx.pertemuan4.ListBelajar
import com.example.bila_oronyx.pertemuan4.Profil
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

        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        binding.btnRumus.setOnClickListener {
            val intent = Intent(this, RumusBangun::class.java)
            intent.putExtra("judul", "Kalkulator Geometrik")
            intent.putExtra("deskripsi", "Hitung luas dan volume dengan mudah")
            startActivity(intent)
        }

        binding.btnProfil.setOnClickListener {
            val intent = Intent(this, Profil::class.java)
            intent.putExtra("judul", "Profil Pengguna")
            startActivity(intent)
        }

        binding.btnList.setOnClickListener {
            val intent = Intent(this, ListBelajar::class.java)
            intent.putExtra("judul", "Daftar Tugas Belajar")
            startActivity(intent)
        }

        binding.btnWebView.setOnClickListener {
            val intent = Intent(this, BeritaWebActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    val editor = sharedPref.edit()
                    editor.clear()
                    editor.apply()

                    dialog.dismiss()

                    val intent = Intent(this, LoginOronyx::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                    Snackbar.make(binding.root, "Logout dibatalkan", Snackbar.LENGTH_SHORT).show()
                }
                .show()
        }
    }
}