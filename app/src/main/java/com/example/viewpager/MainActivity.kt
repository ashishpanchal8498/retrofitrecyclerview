package com.example.viewpager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private var baseurl = "https://api.github.com"

    private val tabTitle = arrayOf("Friends", "Groups")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pager = findViewById<ViewPager2>(R.id.viewPager)
        val tabular = findViewById<TabLayout>(R.id.tabLayout)
        pager.adapter = TabAdapter(supportFragmentManager, lifecycle)
        TabLayoutMediator(tabular, pager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }
}