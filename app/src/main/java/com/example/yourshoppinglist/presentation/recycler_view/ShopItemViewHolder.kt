package com.example.yourshoppinglist.presentation.recycler_view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yourshoppinglist.R

class ShopItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val name = view.findViewById<TextView>(R.id.name_tv)
    val count = view.findViewById<TextView>(R.id.count_tv)
}