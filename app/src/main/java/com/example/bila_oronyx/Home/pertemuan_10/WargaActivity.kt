// 📁 Home/pertemuan_10/WargaActivity.kt
package com.example.bila_oronyx.Home.pertemuan_10

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bila_oronyx.R
import com.example.bila_oronyx.databinding.ActivityWargaBinding
import com.google.android.material.tabs.TabLayoutMediator

class WargaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWargaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWargaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Data Warga"

        // ViewPager + TabLayout
        val tabsAdapter = WargaTabAdapter(this)
        binding.viewPager.adapter = tabsAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Nama"
                    tab.icon = androidx.core.content.ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_person
                    )
                }
                1 -> {
                    tab.text = "Rumah"
                    tab.icon = androidx.core.content.ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_home
                    )
                }
            }
        }.attach()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}