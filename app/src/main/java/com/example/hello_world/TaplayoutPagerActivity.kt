package com.example.hello_world

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class TaplayoutPagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taplayout_pager)

        val viewPager = findViewById<ViewPager>(R.id.viewpager)
        val tabLayout = findViewById<TabLayout>(R.id.tablayout)

        //tabLayout에 tab을 추가하는 방법
        tabLayout.addTab(tabLayout.newTab().setText("메뉴"))
        tabLayout.addTab(tabLayout.newTab().setText("금액"))
        tabLayout.addTab(tabLayout.newTab().setText("배달"))

        //Pager에 Adapter를 장착하는 방법
        viewPager.adapter = FragmentPagerAdapter(supportFragmentManager,3)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                viewPager.setCurrentItem(tab!!.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })




    }
}

class FragmentPagerAdapter(
    fragmentManager: FragmentManager,
    val tabCount: Int
) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return tabCount
    }

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return FragmentOne()
            1 -> return FragmentOne()
            else -> return FragmentOne()
        }
    }
}
