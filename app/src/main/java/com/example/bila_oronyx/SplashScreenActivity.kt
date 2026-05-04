package com.example.bila_oronyx

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.bila_oronyx.pertemuan3.LoginOronyx
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
        val isLogin = sharedPref.getBoolean("isLogin", false)

        lifecycleScope.launch {
            delay(2000)

            if (isLogin) {
                val intent = Intent(this@SplashScreenActivity, BaseActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@SplashScreenActivity, LoginOronyx::class.java)
                startActivity(intent)
            }
            finish()
        }
    }
}