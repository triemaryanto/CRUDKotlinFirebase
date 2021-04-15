package com.example.verivikator

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class Splash : AppCompatActivity() {
    private lateinit var bgapp : ImageView
    private lateinit var logo : ImageView
    private lateinit var splashtext : LinearLayout
    private lateinit var hometext : LinearLayout
    private lateinit var menus : LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.splash)

        val frombottom = AnimationUtils.loadAnimation(this, R.anim.frombotton)

        val ladd = findViewById<LinearLayout>(R.id.ladd)
        val lsee = findViewById<LinearLayout>(R.id.lsee)
        val lpdf = findViewById<LinearLayout>(R.id.lpdf)

        ladd.setOnClickListener{
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
        lsee.setOnClickListener{
            startActivity(Intent(applicationContext, ViewData::class.java))
        }
        lpdf.setOnClickListener{
            startActivity(Intent(applicationContext, PdfView::class.java))
        }




        bgapp = findViewById(R.id.bgapp)
        logo = findViewById(R.id.logodiman)
        splashtext = findViewById(R.id.splashtext)
        hometext = findViewById(R.id.hometext)
        menus = findViewById(R.id.menus)

        bgapp.animate().translationY(-700F).setDuration(2400).setStartDelay(1200)
        logo.animate().alpha(0F).setDuration(800).setStartDelay(600)
        splashtext.animate().translationY(140F).alpha(0F).setDuration(800).setStartDelay(300)
        hometext.startAnimation(frombottom)
        menus.startAnimation(frombottom)

    }
}