package com.example.hello_world

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

class ResourceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource)

        findViewById<TextView>(R.id.text).setOnClickListener{
            (it as TextView).text = resources.getText(R.string.app_name)
        //   it.background = resources.getDrawable(androidx.constraintlayout.widget.R.drawable.abc_btn_borderless_material)


            it.background = resources.getDrawable(R.drawable.facebook,this.theme)
            //it.background = ContextCompat.getDrawable(this,R.drawable.facebook)
         //   it.background = ResourcesCompat.getDrawable(resources,R.drawable.facebook,null)
        }

        findViewById<ImageView>(R.id.image).setOnClickListener{
            (it as ImageView).setImageDrawable(
                resources.getDrawable(R.drawable.facebook,this.theme)
            )
        }

    }
}