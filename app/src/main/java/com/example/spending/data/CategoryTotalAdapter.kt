package com.example.spending.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spending.R

// Adapter for displaying category totals in a RecyclerView
class CategoryTotalAdapter : RecyclerView.Adapter<CategoryTotalAdapter.ViewHolder>() {

    // Holds the list of CategoryTotal items to be displayed in the RecyclerView
    private var data: List<CategoryTotal> = emptyList()  // Initially, the data is an empty list

    // Method to set the data list and notify RecyclerView to update the views
    fun submitList(list: List<CategoryTotal>) {
        data = list  // Assign the new list to the data field
        notifyDataSetChanged()  // Notify RecyclerView that the data has changed, so it will refresh the view
    }

    // ViewHolder class holds references to the views in each item layout
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // TextView to display the category name
        val txtCategory: TextView = itemView.findViewById(R.id.txtCategory)

        // TextView to display the total amount for the category
        val txtTotal: TextView = itemView.findViewById(R.id.txtTotal)
    }

    // Called when a new ViewHolder is created to hold each item view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the item layout (item_category_total.xml) to create a new view for each item in the list
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_total, parent, false)

        // Return a new ViewHolder instance, passing the inflated view
        return ViewHolder(view)
    }

    // Returns the total number of items in the data list
    override fun getItemCount(): Int = data.size  // The size of the data list dictates the number of items in the RecyclerView

    // Binds the data to the view in each ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the current CategoryTotal object at the given position
        val item = data[position]

        // Set the category name text in the corresponding TextView (txtCategory)
        holder.txtCategory.text = item.category

        // Set the total amount text in the corresponding TextView (txtTotal)
        holder.txtTotal.text = item.total.toString()  // Convert the total value to a string to display it
    }
}
