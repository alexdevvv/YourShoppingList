package com.example.yourshoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.yourshoppinglist.R

class ShopItemActivity : AppCompatActivity() {

    var shopItemId: Int = -1
    var screenMode: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        launchRightScreen()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(STATE_SHOP_ITEM)) {
            throw RuntimeException("Not found STATE_SHOP_ITEM")
        }
        val mode = intent.getStringExtra(STATE_SHOP_ITEM)
        if (mode != ADD_STATE && mode != EDIT_STATE) {
            throw RuntimeException("Unknown mode $mode")
        }
        screenMode = mode
        if (screenMode == EDIT_STATE) {
            shopItemId = intent.getIntExtra(ID_SHOP_ITEM, -1)

        }

    }

    private fun launchRightScreen() {
        val fragment = when (screenMode) {
            ADD_STATE -> ShopItemFragment.newInstanceAddModeFragment()
            EDIT_STATE -> ShopItemFragment.newInstanceEditModeFragment(shopItemId)
            else -> throw RuntimeException("Error launchRightMode method")
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.shop_item_container, fragment)
            .commit()
    }


    companion object {
        const val STATE_SHOP_ITEM = "state_shop_item"
        const val EDIT_STATE = "edit_state"
        const val ADD_STATE = "add_state"
        const val ID_SHOP_ITEM = "id_shop_item"

        fun addNewShopItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(STATE_SHOP_ITEM, ADD_STATE)
            return intent
        }

        fun editShopItem(context: Context, id: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(STATE_SHOP_ITEM, EDIT_STATE)
            intent.putExtra(ID_SHOP_ITEM, id)
            return intent
        }
    }


}