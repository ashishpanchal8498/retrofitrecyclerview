package com.example.viewpager

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private var baseurl = "https://api.github.com"

    private val tabTitle = arrayOf("Friends", "Groups")

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerAdapter
    private var data: List<UsersItem> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        adapter = RecyclerAdapter(this, data)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        getAllData()

        val pager = findViewById<ViewPager2>(R.id.viewPager)
        val tabular = findViewById<TabLayout>(R.id.tabLayout)
        pager.adapter = TabAdapter(supportFragmentManager, lifecycle)
        TabLayoutMediator(tabular, pager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    private fun getAllData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retroData = retrofit.getData()

        retroData.enqueue(object : Callback<List<UsersItem>> {
            override fun onResponse(
                call: Call<List<UsersItem>>,
                response: Response<List<UsersItem>>
            ) {
                val data = response.body()!!

                adapter = RecyclerAdapter(baseContext, data)

                recyclerView.adapter = adapter

                Log.d("data", data.toString())
            }

            override fun onFailure(call: Call<List<UsersItem>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}