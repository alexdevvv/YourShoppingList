package com.example.yourshoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun editShopItem(shopItem: ShopItem)

    fun deleteShopItem(shopItemId: ShopItem)

    fun addShopItem(shopItem: ShopItem)

    fun getShopItemById(shopItemId: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>
}