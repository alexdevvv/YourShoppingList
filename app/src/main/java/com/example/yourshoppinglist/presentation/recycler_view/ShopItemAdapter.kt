package com.example.yourshoppinglist.presentation.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.yourshoppinglist.R
import com.example.yourshoppinglist.domain.ShopItem

class ShopItemAdapter : RecyclerView.Adapter<ShopItemViewHolder>() {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: OnShopItemClickListener? = null


    var listShopItem = listOf<ShopItem>()
        set(value) {
            val callback = ShopListDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layoutShopItem = when (viewType) {
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

        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }

        holder.itemView.setOnClickListener {
            onShopItemClickListener?.onClick(shopItem)
        }

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

    interface OnShopItemLongClickListener {
        fun onLongClick(shopItem: ShopItem)
    }

    interface OnShopItemClickListener {
        fun onClick(shopItem: ShopItem)
    }

    companion object {
        const val ENABLED_STATE = 1
        const val DISABLED_STATE = -1
    }

}


