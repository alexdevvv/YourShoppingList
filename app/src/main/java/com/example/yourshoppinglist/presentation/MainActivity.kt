package com.example.yourshoppinglist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.yourshoppinglist.R
import com.example.yourshoppinglist.domain.ShopItem
import com.example.yourshoppinglist.presentation.recycler_view.ShopItemAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var vm: MainViewModel
    private lateinit var rv: RecyclerView
    private lateinit var shopItemAdapter: ShopItemAdapter
    private lateinit var shopItemBt: FloatingActionButton
    private var containerHorizontal: FragmentContainerView? = null
//    private  var shopItemFragment = ShopItemFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        containerHorizontal = findViewById(R.id.horizontal_container)
        initAddShopItemButton()
        vm = ViewModelProvider(this)[MainViewModel::class.java]
        setupRecyclerView()
        observeViewModel()

    }

    private fun isHorizontalLaunchState(): Boolean =
        containerHorizontal != null

    private fun observeViewModel() {
        vm.shopList.observe(this) {
            shopItemAdapter.listShopItem = it
        }
    }

    private fun initAddShopItemButton() {
        shopItemBt = findViewById(R.id.floating_action_button)
        shopItemBt.setOnClickListener {
            if (!isHorizontalLaunchState()){
                val intent = ShopItemActivity.addNewShopItem(this)
                startActivity(intent)
            }else{
                fragmentTransactionHorizontal(ShopItemFragment.newInstanceAddModeFragment())
            }
        }
    }

    private fun setupRecyclerView() {
        rv = findViewById(R.id.shop_item_rv)
        shopItemAdapter = ShopItemAdapter()
        with(rv) {
            adapter = shopItemAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopItemAdapter.ENABLED_STATE, 30
            )
            recycledViewPool.setMaxRecycledViews(
                ShopItemAdapter.DISABLED_STATE, 30
            )
        }
        setupOnLongClick()
        setupOnClick()
        deleteOnSwipe()
    }

    private fun deleteOnSwipe() {
        val myCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val shopItem = shopItemAdapter.listShopItem[viewHolder.adapterPosition]
                vm.deleteShopItem(shopItem)
            }
        }

        val itemTouchHelper = ItemTouchHelper(myCallback)
        itemTouchHelper.attachToRecyclerView(rv)
    }

    private fun setupOnClick() {
        shopItemAdapter.onShopItemClickListener = object : ShopItemAdapter.OnShopItemClickListener {
            override fun onClick(shopItem: ShopItem) {
                val id = shopItem.id
                if (!isHorizontalLaunchState()) {
                    val intent = ShopItemActivity.editShopItem(applicationContext, id)
                    startActivity(intent)
                }else{
                    fragmentTransactionHorizontal(ShopItemFragment.newInstanceEditModeFragment(id))
                }
            }
        }
    }

    private fun fragmentTransactionHorizontal(fragment: ShopItemFragment){
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.horizontal_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupOnLongClick() {
        shopItemAdapter.onShopItemLongClickListener = {
            vm.editEnableShopItem(it)
        }
    }


}
