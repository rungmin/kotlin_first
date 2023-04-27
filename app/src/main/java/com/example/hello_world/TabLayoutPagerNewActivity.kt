package com.example.hello_world

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

class TabLayoutPagerNewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_layout_pager_new)

        val viewPager = findViewById<ViewPager2>(R.id.viewpager)
        val tabLayout = findViewById<TabLayout>(R.id.tablayout)

        //tabLayout에 tab을 추가하는 방법
        tabLayout.addTab(tabLayout.newTab().setText("메뉴"))
        tabLayout.addTab(tabLayout.newTab().setText("금액"))
        tabLayout.addTab(tabLayout.newTab().setText("배달"))

        //Pager에 Adapter를 장착하는 방법
        viewPager.adapter = FragmentAdapter(this,3)

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

class FragmentAdapter(
    fragmentActivity: FragmentActivity,
    val tabCount: Int
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return tabCount
    }

    override fun createFragment(position: Int): Fragment {

        when(position) {
            0 -> return FragmentOne()
            1 -> return FragmentOne()
            else -> return FragmentOne()


      }
    }
}