package com.example.verivikator

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class Awal: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        // simulate some async initialization like authenticating user from network
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, Splash::class.java))
            finish()
        }, 4000)
}
}