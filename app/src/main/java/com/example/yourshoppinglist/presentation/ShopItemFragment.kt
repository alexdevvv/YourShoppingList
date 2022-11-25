package com.example.yourshoppinglist.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.yourshoppinglist.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {

    private lateinit var saveShopItemBt: Button
    private lateinit var shopItemNameTil: TextInputLayout
    private lateinit var shopItemCountTil: TextInputLayout
    private lateinit var shopItemNameEt: TextInputEditText
    private lateinit var shopItemCountEt: TextInputEditText
    private lateinit var vm: ShopItemViewModel


    private var screenMode: String = MODE_UNKNOVN
    private var shopItemId: Int = UNDEFINED_ID

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initSaveShopItemButton()
        observeViewModel()
        nameEditTextChangeListener()
        countEditTextChangeListener()

        if (screenMode == EDIT_STATE && shopItemId != UNDEFINED_ID) {
            vm.getShopItemById(shopItemId)
        }
    }

    private fun observeViewModel() {
        vm.addShopItemLD.observe(viewLifecycleOwner) {
            requireActivity().onBackPressed()
        }

        vm.editShopItemLd.observe(viewLifecycleOwner) {
            requireActivity().onBackPressed()
        }

        vm.getShopItemByIdLD.observe(viewLifecycleOwner) {
            it.let {
                shopItemNameEt.setText(it.name)
                shopItemCountEt.setText(it.count.toString())
            }
        }

        vm.nameErrorLD.observe(viewLifecycleOwner) {
            if (it != false) {
                shopItemNameTil.error = getString(R.string.error_name)

            } else {
                shopItemNameTil.error = null

            }
        }

        vm.countErrorLD.observe(viewLifecycleOwner) {
            if (it != false) {
                shopItemCountTil.error = getString(R.string.error_count)
            } else {
                shopItemCountTil.error = null
            }
        }
    }

    private fun nameEditTextChangeListener() {
        shopItemNameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                vm.resetNameError()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun countEditTextChangeListener() {
        shopItemCountEt.addTextChangedListener(object : TextWatcher {
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
            when (screenMode) {
                EDIT_STATE -> {
                    val shopitem = vm.getShopItemByIdLD.value
                    val shopItemName = shopItemNameEt.text.toString()
                    val shopItemCount = (shopItemCountEt.text.toString())
                    if (shopitem != null) {
                        val shopItemEnabled = shopitem.enabled
                        val shopItemId = shopitem.id
                        vm.editShopItem(shopItemName, shopItemCount, shopItemId, shopItemEnabled)
                    }


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

    private fun initViews(view: View) {
        saveShopItemBt = view.findViewById(R.id.save_bt)
        shopItemNameEt = view.findViewById(R.id.name_tiet)
        shopItemCountEt = view.findViewById(R.id.count_tiet)
        shopItemNameTil = view.findViewById(R.id.name_til)
        shopItemCountTil = view.findViewById(R.id.count_til)
        vm = ViewModelProvider(this)[ShopItemViewModel::class.java]
    }

    private fun parseParams() {
        val arguments = requireArguments()
        if (!arguments.containsKey(STATE_SHOP_ITEM)) {
            throw RuntimeException("Not found param STATE_SHOP_ITEM")
        }
        if (arguments.getString(STATE_SHOP_ITEM) != EDIT_STATE && arguments.getString(
                STATE_SHOP_ITEM
            ) != ADD_STATE
        ) {
            throw RuntimeException("Undefined param SATE_SHOP_ITEM $screenMode")
        }

        screenMode = arguments.getString(STATE_SHOP_ITEM).toString()

        if (screenMode == EDIT_STATE){
            shopItemId = arguments.getInt(ID_SHOP_ITEM)
        }

    }

    companion object {
        const val STATE_SHOP_ITEM = "state_shop_item"
        const val ID_SHOP_ITEM = "id_shop_item"
        const val EDIT_STATE = "edit_state"
        const val ADD_STATE = "add_state"
        const val MODE_UNKNOVN = ""
        const val UNDEFINED_ID = -1


        fun newInstanceAddModeFragment(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(STATE_SHOP_ITEM, ADD_STATE)
                }
            }
        }

        fun newInstanceEditModeFragment(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(STATE_SHOP_ITEM, EDIT_STATE)
                    putInt(ID_SHOP_ITEM, shopItemId)
                }
            }
        }
    }
}

