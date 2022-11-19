package com.example.yourshoppinglist.presentation.recycler_view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yourshoppinglist.R
import com.example.yourshoppinglist.domain.ShopItem

class ShopItemAdapter : RecyclerView.Adapter<ShopItemViewHolder>() {

    private var countViewHolder = 0

    var listShopItem = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        Log.e("OO_CREATE_VIEW_HOLDER", "${++countViewHolder}")
        val layoutShopItem = when(viewType){
            ENABLED_STATE -> R.layout.item_enabled
            DISABLED_STATE -> R.layout.item_disabled
            else -> throw Exception("Unknown view type $viewType")
        }

        val view = LayoutInflater.from(parent.context).inflate(
            layoutShopItem,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = listShopItem[position]

        holder.name.text = "${shopItem.name}"
        holder.count.text = shopItem.count.toString()

    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = listShopItem[position]
        return if (shopItem.enabled) {
            ENABLED_STATE
        } else {
            DISABLED_STATE
        }
    }

    override fun getItemCount(): Int {
        return listShopItem.size

    }

    companion object {
        const val ENABLED_STATE = 1
        const val DISABLED_STATE = -1
    }

}


