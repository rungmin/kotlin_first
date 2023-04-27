package com.example.hello_world

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class Thread_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread)

        val currentThread = Thread.currentThread()
        Log.d("testt", "" + currentThread)

//        Thread {
//            for (a in 1..1000) {
//                Log.d("testt", "A" + a)
//            }
//        }.start()
//
//        Thread {
//            for (a in 1..1000) {
//                Log.d("testt", "B" + a)
//            }
//        }.start()

        Thread {
            val currentThread = Thread.currentThread()
            Log.d("testt", "A" + currentThread)
            findViewById<TextView>(R.id.test).text = "changed"
            // UI관련 작업을 메인쓰레드가 아닌 쓰레드에서 하려고 하면 해당 작업 메인쓰레드의 gueue로 들어간다
            //  -> 에러가 발생하지 않을 수도 있다
            runOnUiThread {
                findViewById<TextView>(R.id.test).text = "changed"
            }
        }.start()

    }
}
