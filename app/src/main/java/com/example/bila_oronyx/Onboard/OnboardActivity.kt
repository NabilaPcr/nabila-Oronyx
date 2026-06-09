package com.example.bila_oronyx.Onboard

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bila_oronyx.R
import com.example.bila_oronyx.databinding.ActivityBaseBinding
import com.example.bila_oronyx.databinding.ActivityOnboardBinding

// OnboardActivity.kt
class OnboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
        setupDotsIndicator()
    }

    private fun setupViewPager() {
        val fragments = listOf(
            Page1Fragment(),
            Page2Fragment(),
            Page3Fragment()
        )

        val adapter = OnboardAdapter(this, fragments)
        binding.viewPager.adapter = adapter
    }

    private fun setupDotsIndicator() {
        binding.dotIndicator.attachTo(binding.viewPager)
    }
}