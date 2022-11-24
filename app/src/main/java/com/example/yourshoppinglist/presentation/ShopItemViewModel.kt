package com.example.yourshoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yourshoppinglist.data.ShopListRepositoryImpl
import com.example.yourshoppinglist.domain.AddShopItemUseCase
import com.example.yourshoppinglist.domain.EditShopItemUseCase
import com.example.yourshoppinglist.domain.ShopItem

class ShopItemViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)

    private val _editShopItemLD = MutableLiveData<Boolean>()
    val editShopItemLd: LiveData<Boolean>
    get() = _editShopItemLD

    private val _addShopItemLD = MutableLiveData<Boolean>()
    val addShopItemLD: LiveData<Boolean>
    get() = _addShopItemLD

    private val _nameErrorLD = MutableLiveData<Boolean>()
    val nameErrorLD: LiveData<Boolean>
    get() = _nameErrorLD

    private val _countErrorLD = MutableLiveData<Boolean>()
    val countErrorLD: LiveData<Boolean>
        get() = _countErrorLD


    fun editShopItem(name: String?, count: String?, id: Int) {
        if (checkNullForUserInput(name, count)) {
            val shopItemName = validateNameInput(name!!)
            val shopItemCount = validateCountInput(count!!)
            if (shopItemName.isNotEmpty() && shopItemCount != 0) {
                val shopItem = ShopItem(shopItemName, shopItemCount, true, id)
                editShopItemUseCase.editShopItem(shopItem)
                _editShopItemLD.value = true
            }
        }

    }

    fun addShopItem(name: String, count: String) {
        if (checkNullForUserInput(name, count)) {
            val shopItemName = validateNameInput(name)
            val shopItemCount = validateCountInput(count)
            if (shopItemName.isNotEmpty() && shopItemCount != 0){
                val shopItem = ShopItem(shopItemName, shopItemCount, false)
                addShopItemUseCase.addShopItem(shopItem)
                _addShopItemLD.value = true
            }

        }
    }


    private fun checkNullForUserInput(name: String?, count: String?): Boolean {
        var rsl = true
        if (name == null) {
            rsl = false
        } else if (count == null) {
            rsl = false
        }
        return rsl
    }

    private fun validateNameInput(name: String): String {
        return if (name.isNotEmpty()) {
            name.trim()
        } else {
            _nameErrorLD.value = true
            return "" //TODO: validate name error
        }
    }

    private fun validateCountInput(count: String): Int {
        try {
            return count.trim().toInt()
        } catch (e: Exception) {
            _countErrorLD.value = true
        }
        return 0
    }

    fun resetNameError(){
        _nameErrorLD.value = false
    }

    fun resetCountError(){
        _countErrorLD.value = false
    }




}