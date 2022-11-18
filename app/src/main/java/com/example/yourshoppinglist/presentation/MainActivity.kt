package com.example.yourshoppinglist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.yourshoppinglist.R
import com.example.yourshoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var vm: MainViewModel
    private lateinit var ll: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ll = findViewById(R.id.linear_layoute)
        vm = ViewModelProvider(this)[MainViewModel::class.java]
        observeViewModel()

    }

    private fun observeViewModel() {
        vm.shopList.observe(this) {
            initShopItemView(it)
        }
    }

    private fun initShopItemView(list: List<ShopItem>){
        ll.removeAllViews()
        for (shopItem in list){
            val layoutId = if (shopItem.enabled){
                R.layout.item_enabled
            }else{
                R.layout.item_disabled
            }
            val itemView = LayoutInflater.from(this).inflate(layoutId, ll, false)
            val itemName = itemView.findViewById<TextView>(R.id.name_tv)
            val itemCount = itemView.findViewById<TextView>(R.id.count_tv)
            itemName.text = shopItem.name
            itemCount.text = shopItem.count.toString()

            itemView.setOnLongClickListener{
                vm.editShopItem(shopItem)
                true
            }

            ll.addView(itemView)
        }
    }

}
