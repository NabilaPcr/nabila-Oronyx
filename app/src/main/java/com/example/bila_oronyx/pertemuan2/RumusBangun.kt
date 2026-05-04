package com.example.bila_oronyx.pertemuan2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bila_oronyx.R
import com.example.bila_oronyx.databinding.ActivityMainBinding
import com.example.bila_oronyx.databinding.ActivityRumusBangunBinding

class RumusBangun : AppCompatActivity() {
    private lateinit var binding: ActivityRumusBangunBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRumusBangunBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.button2.setOnClickListener {
            // Logic untuk Segitiga
            val alas = binding.etAlas.text.toString().toDoubleOrNull() ?: 0.0
            val tinggi = binding.etTinggi.text.toString().toDoubleOrNull() ?: 0.0

            val luas = 0.5 * alas * tinggi

            binding.tvHasilSegitiga.text = "Hasil Luas: $luas"

            Log.d("HASIL_HITUNG", "Hitung Segitiga: $luas")
        }

        binding.button2.setOnClickListener {
            val alas = binding.etAlas.text.toString().toDoubleOrNull() ?: 0.0
            val tinggi = binding.etTinggi.text.toString().toDoubleOrNull() ?: 0.0
            val luas = 0.5 * alas * tinggi

            binding.tvHasilSegitiga.text = "Hasil Luas: $luas"

            Toast.makeText(this, "Input dulu yaa angkanya", Toast.LENGTH_SHORT).show()

            Log.d("HASIL_HITUNG", "Hitung Segitiga: $luas")
        }

        binding.button3.setOnClickListener {
            val sisi = binding.etSisi.text.toString().toDoubleOrNull() ?: 0.0
            val volume = sisi * sisi * sisi

            binding.tvHasilKubus.text = "Hasil Volume: $volume"
            Toast.makeText(this, "Input dulu ya angkanya", Toast.LENGTH_SHORT).show()

            Log.d("HASIL_HITUNG", "Hitung Kubus: $volume")
        }
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, ActivityMainBinding::class.java)
            startActivity(intent)
        }
    }
}