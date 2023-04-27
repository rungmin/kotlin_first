package com.example.hello_world

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class nextPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_page)

        val name: String = intent.extras?.getString("name")?: ""
        val number: String = intent.extras?.getString("number")?: ""

        findViewById<TextView>(R.id.name).text = name
        findViewById<TextView>(R.id.number).text = number

    }
}