package com.example.yourshoppinglist.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.yourshoppinglist.R

class MainActivity : AppCompatActivity() {

    private lateinit var vm: MainViewModel
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm = ViewModelProvider(this)[MainViewModel::class.java]
        observeViewModel()

    }

    private fun observeViewModel() {
        vm.shopList.observe(this) {
            Log.e("XXX", it.toString())
            if(count == 0) {
                val item = it[0]
                vm.editShopItem(item)
                count++
            }

        }

    }
}