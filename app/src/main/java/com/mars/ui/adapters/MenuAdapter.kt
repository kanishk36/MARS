package com.mars.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.mars.R
import com.mars.models.MenuOption


class MenuAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val selectedMenu: MutableLiveData<Int> = MutableLiveData()
    private var mMenuList: MutableList<MenuOption>

    init {
        mMenuList = ArrayList()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_menu_item_layout, parent, false)
        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mMenuList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val menu = mMenuList[position]

        val menuViewHolder = holder as MenuViewHolder

        menuViewHolder.sideMenuTitle.text = menu.name
        holder.itemView.setOnClickListener({
            selectedMenu.value = menu.id
        })

    }

    fun setMenuList(@NonNull menuList: ArrayList<MenuOption>) {
        mMenuList = menuList
        notifyDataSetChanged()
    }

    private inner class MenuViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var sideMenuTitle: TextView

        init {
            sideMenuTitle = itemView.findViewById(R.id.sideMenuTitle)
        }
    }
}