package com.example.hello_world

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout

class ListViewHomework : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_homework)

        val listView1 = findViewById<ListView>(R.id.listView1)
        val tablayout1 = findViewById<TabLayout>(R.id.tablayout1)

        tablayout1.addTab(tablayout1.newTab().setText("순위"))
        tablayout1.addTab(tablayout1.newTab().setText("레시피 만들기"))
        tablayout1.addTab(tablayout1.newTab().setText("저장된 레시피"))
        tablayout1.addTab(tablayout1.newTab().setText("메뉴"))

        }


    }






