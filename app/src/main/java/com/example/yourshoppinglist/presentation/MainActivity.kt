package com.example.yourshoppinglist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.yourshoppinglist.R
import com.example.yourshoppinglist.domain.ShopItem
import com.example.yourshoppinglist.presentation.recycler_view.ShopItemAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var vm: MainViewModel
    private lateinit var rv: RecyclerView
    private lateinit var adapter: ShopItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vm = ViewModelProvider(this)[MainViewModel::class.java]
        setupRecyclerView()
        observeViewModel()

    }

    private fun observeViewModel() {
        vm.shopList.observe(this) {
            adapter.listShopItem = it
        }
    }

    private fun setupRecyclerView(){
        rv = findViewById(R.id.shop_item_rv)
        adapter = ShopItemAdapter()
        rv.adapter = adapter

    }


}
