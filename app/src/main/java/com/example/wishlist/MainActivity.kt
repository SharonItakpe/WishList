package com.example.wishlist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var wishItems: MutableList<WishItem>
    private lateinit var adapter: WishListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the RecyclerView and other views
        val wishRecyclerView = findViewById<RecyclerView>(R.id.wishRecyclerView)
        val itemNameInput = findViewById<EditText>(R.id.itemNameInput)
        val itemPriceInput = findViewById<EditText>(R.id.itemPriceInput)
        val itemUrlInput = findViewById<EditText>(R.id.itemUrlInput)
        val submitButton = findViewById<Button>(R.id.submitButton)

        // Initialize the wish list and the adapter
        wishItems = mutableListOf()
        adapter = WishListAdapter(wishItems, { item ->
            // Handle normal click to open the URL
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                ContextCompat.startActivity(this, browserIntent, null)
            } catch (e: Exception) {
                Toast.makeText(this, "Invalid URL for " + item.name, Toast.LENGTH_LONG).show()
            }
        }, { item ->
            // Handle long click (remove item)
            val index = wishItems.indexOf(item)
            if (index != -1) {
                wishItems.removeAt(index)
                adapter.notifyItemRemoved(index)
            }
        })

        // Set up the RecyclerView
        wishRecyclerView.adapter = adapter
        wishRecyclerView.layoutManager = LinearLayoutManager(this)

        // Set up the submit button to add new items to the wish list
        submitButton.setOnClickListener {
            val itemName = itemNameInput.text.toString()
            val itemPrice = itemPriceInput.text.toString().toDoubleOrNull()
            val itemUrl = itemUrlInput.text.toString()

            if (itemName.isBlank() || itemPrice == null || itemUrl.isBlank()) {
                Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
            } else {
                val newItem = WishItem(itemName, itemPrice, itemUrl)
                wishItems.add(newItem)
                adapter.notifyItemInserted(wishItems.size - 1)

                // Clear the input fields
                itemNameInput.text.clear()
                itemPriceInput.text.clear()
                itemUrlInput.text.clear()
            }
        }
    }
}
