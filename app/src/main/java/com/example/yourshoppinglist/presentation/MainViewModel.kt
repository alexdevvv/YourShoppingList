package com.example.yourshoppinglist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yourshoppinglist.data.ShopListRepositoryImpl
import com.example.yourshoppinglist.domain.DeleteShopItemUseCase
import com.example.yourshoppinglist.domain.EditShopItemUseCase
import com.example.yourshoppinglist.domain.GetShopListUseCase
import com.example.yourshoppinglist.domain.ShopItem

class MainViewModel : ViewModel() {

    private val shopListRepository = ShopListRepositoryImpl
    private val getShopListUseCase = GetShopListUseCase(shopListRepository)
    private val deleShopItemUseCase = DeleteShopItemUseCase(shopListRepository)
    private val editShopItemUseCase = EditShopItemUseCase(shopListRepository)

    val getShopListLD = MutableLiveData<List<ShopItem>>()
    val deleteShopItemLD = MutableLiveData<Boolean>()


    val shopList = getShopListUseCase.getShopList()


    fun deleteShopItem(shopItem: ShopItem) {
        deleShopItemUseCase.deleteShopItem(shopItem)

    }

    fun editShopItem(shopItem: ShopItem) {
        val newShopItem = ShopItem(shopItem.name, shopItem.count, !shopItem.enabled , shopItem.id)
        editShopItemUseCase.editShopItem(newShopItem)

    }
}