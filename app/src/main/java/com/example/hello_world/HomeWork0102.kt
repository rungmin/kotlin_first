package com.example.hello_world

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat

class HomeWork0102 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_work0102)

        var numberList = mutableListOf<Number>()


        for(i in 0..50) {

            numberList.add(com.example.hello_world.Number("" +i + "번째 사람","" + "010-000-000"+i ))
        }



        val container1 = findViewById<LinearLayoutCompat>(R.id.container1)
        val inflater = LayoutInflater.from(this@HomeWork0102)

        numberList.forEach { number ->
            val kakaoItemView = inflater.inflate(R.layout.test_homework, null)
            val kakaoImage = kakaoItemView.findViewById<ImageView>(R.id.kakaoImage)
            val nthPeople = kakaoItemView.findViewById<TextView>(R.id.nthPeople)
            val nthNumber = kakaoItemView.findViewById<TextView>(R.id.nthNumber)

            kakaoImage.setImageDrawable(resources.getDrawable(R.drawable.kakao,this.theme))
            nthPeople.text = number.nthPeople
            nthNumber.text = number.nthNumber
            container1.addView(kakaoItemView)

            kakaoItemView.setOnClickListener{
                startActivity(Intent(this,nextPage::class.java).apply {
                    this.putExtra("name", number.nthPeople)
                    this.putExtra("number",number.nthNumber)
                })

            }
        }


    }
}

class Number(val nthPeople : String , val nthNumber : String)