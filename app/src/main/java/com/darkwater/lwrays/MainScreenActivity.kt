package com.darkwater.lwrays

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.darkwater.lwrays.adapters.PagerAdapter
import com.darkwater.lwrays.network.APIClient
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainScreenActivity : AppCompatActivity() {

    val client = APIClient(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        val tabMenu = findViewById<TabLayout>(R.id.mainScreenTabLayout)
        val viewPager2 = findViewById<ViewPager2>(R.id.mainScreenViewPager2)
        val adapter = PagerAdapter(supportFragmentManager, lifecycle)
        viewPager2.adapter = adapter
        TabLayoutMediator(tabMenu, viewPager2) { tab, position  ->
            when (position) {
                0 -> tab.text = "Рейд"
                1 -> tab.text = "Сообщества"
            }
        }.attach()
    }
}