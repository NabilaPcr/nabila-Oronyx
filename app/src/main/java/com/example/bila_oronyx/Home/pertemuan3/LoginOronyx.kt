package com.example.bila_oronyx.Home.pertemuan3

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bila_oronyx.Home.HomeFragment
import com.example.bila_oronyx.MainActivity
import com.example.bila_oronyx.R
import com.example.bila_oronyx.databinding.ActivityLoginOronyxBinding

class LoginOronyx : AppCompatActivity() {

    private lateinit var binding: ActivityLoginOronyxBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginOronyxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi SharedPreferences
        sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        // Cek apakah sudah login sebelumnya
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        // ============ LOGIN ============
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Ambil data dari SharedPreferences
            val savedUsername = sharedPref.getString("username", "")
            val savedPassword = sharedPref.getString("password", "")

            // Validasi
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Isi semua kolom!", Toast.LENGTH_SHORT).show()
            } else if (username == savedUsername && password == savedPassword) {
                // Login Sukses
                sharedPref.edit().putBoolean("isLoggedIn", true).apply()

                Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()

                // Pindah ke MainActivity (Home)
                val intent = Intent(this, HomeFragment::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Username atau Password salah!", Toast.LENGTH_SHORT).show()
            }
        }

        // ============ Tombol ke Register ============
        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }

        // ============ Lupa Password ============
        binding.tvForgotPassword.setOnClickListener {
            Toast.makeText(this, "Hubungi admin untuk reset password", Toast.LENGTH_SHORT).show()
        }
    }
}