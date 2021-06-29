package com.drians.finalmoviecatalogue.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.drians.finalmoviecatalogue.R
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window
        lifecycleScope.launch {
            delay(2000)
            startActivity(Intent(this@SplashScreenActivity, HomeActivity::class.java))
            finish()
        }
    }
}