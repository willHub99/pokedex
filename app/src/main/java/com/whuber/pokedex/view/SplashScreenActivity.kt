package com.whuber.pokedex.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.whuber.pokedex.R
import com.whuber.pokedex.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private  lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivIconPokedex.alpha = 0f
        val animation = AnimationUtils.loadAnimation(this, R.anim.splash_screen_rotation_animation)
        binding.ivIconPokedex.startAnimation(animation)

        binding.ivIconPokedex.animate().setDuration(3000).alpha(1f).withEndAction {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

    }
}