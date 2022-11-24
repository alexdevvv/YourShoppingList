package com.example.yourshoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.yourshoppinglist.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

    private lateinit var saveShopItemBt: Button
    private lateinit var shopItemNameTil: TextInputLayout
    private lateinit var shopItemCountTil: TextInputLayout
    private lateinit var shopItemNameEt: TextInputEditText
    private lateinit var shopItemCountEt: TextInputEditText

    private var id = 0

    private lateinit var vm: ShopItemViewModel
    private var screen_state: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        initViews()
        parseIntent(intent)
        initSaveShopItemButton()
        observeViewModel()
        nameEditTextChangeListener()
        countEditTextChangeListener()

        if (screen_state == EDIT_STATE) {
            shopItemNameEt.setText(intent.extras?.getString(NAME_SHOP_ITEM).toString())
            shopItemCountEt.setText(intent.extras?.getInt(COUNT_SHOP_ITEM).toString())
            id = intent.extras?.getInt(ID_SHOP_ITEM)!!
        }

    }

    private fun observeViewModel() {
        vm.addShopItemLD.observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
        }

        vm.editShopItemLd.observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
        }

        vm.nameErrorLD.observe(this) {
            if (it != false){
                shopItemNameTil.error = getString(R.string.error_name)

            }else{
                shopItemNameTil.error = null

            }
        }

        vm.countErrorLD.observe(this){
            if (it != false){
                shopItemCountTil.error = getString(R.string.error_count)
            }else{
                shopItemCountTil.error = null
            }
        }
    }

    private fun nameEditTextChangeListener(){
        shopItemNameEt.addTextChangedListener (object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                vm.resetNameError()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun countEditTextChangeListener(){
        shopItemCountEt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                vm.resetCountError()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }




    private fun initSaveShopItemButton() {
        saveShopItemBt.setOnClickListener {
            when (screen_state) {
                EDIT_STATE -> {
                    val shopItemName = shopItemNameEt.text.toString()
                    val shopItemCount = (shopItemCountEt.text.toString())
                    vm.editShopItem(shopItemName, shopItemCount, id)
                }
                ADD_STATE -> {
                    val shopItemName = shopItemNameEt.text.toString()
                    val shopItemCount = shopItemCountEt.text.toString()
                    vm.addShopItem(shopItemName, shopItemCount)
                }
                else -> {
                    throw RuntimeException("Unknown state for ShopItemActivity")
                }
            }

        }
    }

    private fun initViews() {
        saveShopItemBt = findViewById(R.id.save_bt)
        shopItemNameEt = findViewById(R.id.name_tiet)
        shopItemCountEt = findViewById(R.id.count_tiet)
        shopItemNameTil = findViewById(R.id.name_til)
        shopItemCountTil = findViewById(R.id.count_til)
        vm = ViewModelProvider(this)[ShopItemViewModel::class.java]
    }

    private fun parseIntent(intent: Intent) {
        if (!intent.hasExtra(STATE_SHOP_ITEM)) {
            throw RuntimeException("Not found param SCREEN_MODE")
        }
        val mode = intent.getStringExtra(STATE_SHOP_ITEM)
        if (mode != EDIT_STATE && mode != ADD_STATE) {
            throw RuntimeException("Undefined param SATE_SHOP_ITEM $mode")
        }
        screen_state = mode

    }

    companion object {
        const val STATE_SHOP_ITEM = "state_shop_item"
        const val EDIT_STATE = "edit_state"
        const val ADD_STATE = "add_state"
        const val NAME_SHOP_ITEM = "name_shop_item"
        const val COUNT_SHOP_ITEM = "count_chop_item"
        const val ID_SHOP_ITEM = "id_shop_item"

        fun addNewShopItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(STATE_SHOP_ITEM, ADD_STATE)
            return intent
        }

        fun editShopItem(context: Context, name: String, count: Int, id: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(STATE_SHOP_ITEM, EDIT_STATE)
            intent.putExtra(NAME_SHOP_ITEM, name)
            intent.putExtra(COUNT_SHOP_ITEM, count)
            intent.putExtra(ID_SHOP_ITEM, id)
            return intent

        }
    }


}