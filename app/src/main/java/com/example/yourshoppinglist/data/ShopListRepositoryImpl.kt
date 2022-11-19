package com.example.yourshoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yourshoppinglist.domain.ShopItem
import com.example.yourshoppinglist.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {

    private var autoIncrementId = 0

    private val shopListLiveDate = MutableLiveData<List<ShopItem>>()

    private val shoppingList = sortedSetOf<ShopItem>({o1, o2 -> o1.id.compareTo(o2.id)})

    init {
        for (i in 0 until 10000) {
            val shopItem = (ShopItem("ShopItem $i", i, Random.nextBoolean()))
            addShopItem(shopItem)
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shoppingList.add(shopItem)
        updateShopList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldShopItem = getShopItemById(shopItem.id)
        shoppingList.remove(oldShopItem)
        addShopItem(shopItem)
    }

    override fun deleteShopItem(shopItemId: ShopItem) {
        shoppingList.remove(shopItemId)
        updateShopList()
    }

    override fun getShopItemById(shopItemId: Int): ShopItem {
        return shoppingList.find {
            it.id == shopItemId
        } ?: throw RuntimeException("Елемент с id $shopItemId не найден")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLiveDate
    }

    private fun updateShopList() {
        shopListLiveDate.value = shoppingList.toList()
    }
}