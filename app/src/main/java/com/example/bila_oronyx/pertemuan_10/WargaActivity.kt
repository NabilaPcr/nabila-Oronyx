package com.example.bila_oronyx.pertemuan_10

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bila_oronyx.R
import com.example.bila_oronyx.databinding.ActivityWargaBinding
import com.google.android.material.tabs.TabLayoutMediator

class WargaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWargaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityWargaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Website Berita"
            subtitle = "Berita Terpercaya tersedia disini!"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // Membantu fungsi tombol back pada toolbar kembali ke halaman sebelumnya
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // 1. Inisialisasi Adapter
        val tabsAdapter = WargaTabAdapter(this)

        // 2. Set adapter ke ViewPager2
        binding.viewPager.adapter = tabsAdapter

        // 3. Hubungkan TabLayout & ViewPager2 menggunakan Adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            // Atur judul untuk setiap tab
            when (position) {
                0 -> tab.text = "Nama Warga"
                1 -> tab.text = "Rumah Warga"
            }
        }.attach()
    }
}