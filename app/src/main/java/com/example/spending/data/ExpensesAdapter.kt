package com.example.spending.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spending.R

// Adapter to bind Expense data to RecyclerView
class ExpensesAdapter : RecyclerView.Adapter<ExpensesAdapter.ExpenseViewHolder>() {

    // List of expenses to display
    private var expenseList: List<Expense> = emptyList()

    // Method to update the list of expenses and notify the adapter to refresh the RecyclerView
    fun submitList(list: List<Expense>) {
        expenseList = list
        notifyDataSetChanged()  // Notify adapter that data has changed
    }

    // Creates a new ViewHolder (referred to as `ExpenseViewHolder` here) for each item in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        // Inflate the item_expense layout to create the view for each item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)  // Return the view holder for the inflated view
    }

    // Binds the data (expense) to the ViewHolder at a specific position in the list
    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenseList[position]  // Get the expense at the current position
        holder.bind(expense)  // Bind the expense data to the view holder
    }

    // Returns the total number of items in the expense list
    override fun getItemCount(): Int = expenseList.size

    // ViewHolder class to represent a single item in the RecyclerView
    inner class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Reference to the TextViews in the item_expense layout
        private val tvDate: TextView = itemView.findViewById(R.id.tvExpenseDate)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvExpenseDescription)
        private val tvAmount: TextView = itemView.findViewById(R.id.tvExpenseAmount)

        // Method to bind the expense data to the views
        fun bind(expense: Expense) {
            // Set the date of the expense
            tvDate.text = expense.date

            // Set the description of the expense
            tvDescription.text = expense.description

            // Set the amount of the expense (prefix "R" for currency symbol)
            tvAmount.text = "R${expense.amount}"
        }
    }
}
