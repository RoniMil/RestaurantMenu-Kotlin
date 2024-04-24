package com.example.midtermdrill

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// class for managing menu items for use with RecyclerView, extends RecyclerView.Adapter class
class MenuAdapter(private val menuItems: List<MenuItem>) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    // inner class that holds references to the menu item's views (for each attribute)
    class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.itemName)
        val itemDescription: TextView = view.findViewById(R.id.itemDescription)
        val itemPrice: TextView = view.findViewById(R.id.itemPrice)
        val itemImage: ImageView = view.findViewById(R.id.itemImage)
    }

    // creates view holder instances (views) from the menu item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_item, parent, false)
        return MenuViewHolder(view)
    }

    // binds menu item data to it's view holder
    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menuItem = menuItems[position]
        holder.itemName.text = menuItem.name
        holder.itemDescription.text = menuItem.description
        holder.itemPrice.text = menuItem.price
        // assuming image is a drawable resource ID, convert it to int before use
        holder.itemImage.setImageResource(menuItem.imageUrl.toInt())
    }

    // returns the number of menu items in the adapter
    override fun getItemCount() = menuItems.size
}
