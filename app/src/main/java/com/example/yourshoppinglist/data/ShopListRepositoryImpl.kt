package com.example.yourshoppinglist.data

import com.example.yourshoppinglist.domain.ShopItem
import com.example.yourshoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl: ShopListRepository {

    private var autoIncrementId = 0

    private val shoppingList = mutableListOf<ShopItem>()

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID){
            shopItem.id = autoIncrementId++
        }
        shoppingList.add(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
       val oldShopItem =  getShopItemById(shopItem.id)

        shoppingList.remove(oldShopItem)
        shoppingList.add(shopItem)
    }

    override fun deleteShopItem(shopItemId: ShopItem) {
        shoppingList.remove(shopItemId)
    }


    override fun getShopItemById(shopItemId: Int): ShopItem {
      return  shoppingList.find {
          it.id == shopItemId } ?: throw RuntimeException("Елемент с id $shopItemId не найден")
    }

    override fun getShopList(): List<ShopItem> {
        return shoppingList.toList()
    }
}