package com.example.hello_world

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class Application_Activity01 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application01)

        findViewById<TextView>(R.id.changeActivity).setOnClickListener {
            startActivity(Intent(this,Application_Activity02 ::class.java))
        }

        findViewById<TextView>(R.id.testMethod).setOnClickListener{
            (applicationContext as MasterApplication).methodFromApplication()
            val userID = (applicationContext as MasterApplication).userID
            Log.d("testt","user ID is " + userID)
        }


    }
}