package com.example.yourshoppinglist.domain

interface ShopListRepository {

    fun getShopItem(): ShopItem

    fun deleteShopItem(shopItemId: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItemById(shopItemId: Int): ShopItem

    fun getShopList(): List<ShopItem>
}