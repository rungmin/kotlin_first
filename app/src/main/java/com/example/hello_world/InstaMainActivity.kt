package com.example.hello_world

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.InstaProfileFragement
import com.google.android.material.tabs.TabLayout
import java.util.*
import java.util.zip.Inflater

class InstaMainActivity : AppCompatActivity() {
    val instaPostFragment = InstaPostFragment()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insta_main)

        val tablayout = findViewById<TabLayout>(R.id.main_tap)
        val pager = findViewById<ViewPager2>(R.id.main_pager)


        tablayout.addTab(tablayout.newTab().setIcon(R.drawable.btn_outsta_home))
        tablayout.addTab(tablayout.newTab().setIcon(R.drawable.btn_outsta_post))
        tablayout.addTab(tablayout.newTab().setIcon(R.drawable.btn_outsta_my))

        pager.adapter = InstaMainPagerAdapter(this,3,instaPostFragment)

        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                pager.setCurrentItem(tab!!.position)
                if (tab.position == 1) {

                    instaPostFragment.makePost()
                }

            }


            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

    }
}

class InstaMainPagerAdapter(
    fragmentActivity: FragmentActivity,
    val tabCount:Int,
    val instaPostFragment: InstaPostFragment
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return tabCount
    }

    override fun createFragment(position: Int): Fragment {

        when(position) {
            0 -> return InstaFeedFragment()
            1 -> {
                return instaPostFragment
            }
            else -> return InstaProfileFragement()
        }
    }
}
