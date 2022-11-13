package com.example.yourshoppinglist.domain

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopList(): List<ShopItem> =
        shopListRepository.getShopList()

}