package com.example.wishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WishListAdapter(
    private val wishItems: List<WishItem>,
    private val onItemClick: (WishItem) -> Unit,
    private val onItemLongClick: (WishItem) -> Unit
) : RecyclerView.Adapter<WishListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.wish_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = wishItems[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = wishItems.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemNameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        private val itemPriceTextView: TextView = itemView.findViewById(R.id.itemPriceTextView)
        private val itemStoreTextView: TextView = itemView.findViewById(R.id.itemStoreTextView)

        fun bind(item: WishItem) {
            itemNameTextView.text = item.name
            itemPriceTextView.text = item.price.toString()
            itemStoreTextView.text = "Some Store"

            itemView.setOnClickListener {
                onItemClick(item)
            }

            itemView.setOnLongClickListener {
                onItemLongClick(item)
                true
            }
        }
    }
}
