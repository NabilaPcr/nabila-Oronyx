package com.example.bila_oronyx

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bila_oronyx.Setting.SettingFragment
import com.example.bila_oronyx.About.AboutFragment
import com.example.bila_oronyx.Home.HomeFragment
import com.example.bila_oronyx.Onboard.OnboardActivity
import com.example.bila_oronyx.Profile.ProfileFragment
import com.example.bila_oronyx.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        val isLoggedIn = sharedPref.getString("username", null) != null

        if (!isLoggedIn) {
            val intent = Intent(this, OnboardActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.about -> replaceFragment(AboutFragment())
                R.id.profil -> replaceFragment(ProfileFragment())
                R.id.setting -> replaceFragment(SettingFragment())
                R.id.note -> {
                    replaceFragment(FragmentNote())
                    true
                }

            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}