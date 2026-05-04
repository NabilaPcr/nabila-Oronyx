package com.example.bila_oronyx.pertemuan3

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bila_oronyx.pertemuan3.LoginOronyx
import com.example.bila_oronyx.R
import com.example.bila_oronyx.databinding.ActivityWelcomeBinding

class Welcome : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

       binding.btnKembali.setOnClickListener {
            val intent = Intent(this, LoginOronyx::class.java)
            startActivity(intent)
        }
        binding.btnRegis.setOnClickListener {
            val intent = Intent(this, LoginOronyx::class.java)
            startActivity(intent)
        }
    }
}