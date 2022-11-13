package com.example.yourshoppinglist.domain

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopItem(): ShopItem =
        shopListRepository.getShopItem()

}