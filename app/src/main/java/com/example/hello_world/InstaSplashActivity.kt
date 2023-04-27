package com.example.hello_world

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class InstaSplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insta_splash)
        var sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        var token = sharedPreferences.getString("token", "space")
        Log.d("instaa",token!!)
        when (token) {
            "space" -> {
                //로그인이 되어 있지 않는 경우
                startActivity(Intent(this, InstaLoginActivity::class.java))
            }
            else -> {
                //로그인이 되어 있는 경우
                startActivity(Intent(this,InstaMainActivity::class.java))
            }
        }
    }
}