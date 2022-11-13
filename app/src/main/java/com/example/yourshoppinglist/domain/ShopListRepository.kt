package com.example.yourshoppinglist.domain

interface ShopListRepository {

    fun editShopItem(shopItem: ShopItem)

    fun deleteShopItem(shopItemId: ShopItem)

    fun addShopItem(shopItem: ShopItem)

    fun getShopItemById(shopItemId: Int): ShopItem

    fun getShopList(): List<ShopItem>
}