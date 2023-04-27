package com.example.hello_world

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class TabLayoutPagerSimpleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_layout_pager_simple)

        val viewPager = findViewById<ViewPager>(R.id.viewpager)
        val tabLayout = findViewById<TabLayout>(R.id.tablayout)

        //tabLayout에 tab을 추가하는 방법
        tabLayout.addTab(tabLayout.newTab().setText("메뉴"))
        tabLayout.addTab(tabLayout.newTab().setText("금액"))
        tabLayout.addTab(tabLayout.newTab().setText("배달"))

        //Pager에 Adapter를 장착하는 방법
        viewPager.adapter = ViewPagerAdapter(LayoutInflater.from(this),3)

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

class ViewPagerAdapter(
    val layoutInflater: LayoutInflater,
    val tabCount : Int
) : PagerAdapter() {
    override fun getCount(): Int {
        return tabCount
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View
        // == -> 값을 비교한다
        // === -> 주소값을 비교한다
        // a = 1 , b = 1 -> a == b(True), a === b (True,False)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        when(position) {
            0 -> {
                val view = layoutInflater.inflate(R.layout.car_item,container,false)
                container.addView(view)
                return view
            }

            1 -> {
                val view = layoutInflater.inflate(R.layout.subhomework, container, false)
                container.addView(view)
                return view
            }
            else -> {
                val view = layoutInflater.inflate(R.layout.activity_async, container, false)
                container.addView(view)
                return view
            }

        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)


    }
}

